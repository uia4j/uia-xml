uia-xml
===

## 说明

采用 StAX API 进行 XML 与 Object 之间的读写转换。

[English](README.md)


## 标示

可用来定义 XML 元素的标示（Annotation）包括：

* @TagInfo
* @AttrInfo
* @PropInfo
* @ContentInfo
* @XmlInfo
* @TagListInfo
* @TagListElem

### @TagInfo

定义一个 XML 元素。

参数:

* name - 元素名称，可缺省。

范例：

```xml
<Book />
```
```java
@TagInfo(name = "Book")
public class Book {}
```

### @AttrInfo

定义元素的属性，可用得资料型别包括：

* long
* int
* short
* byte
* boolean
* double
* float
* String
* BigDecimal

参数：

* name - 属性名称，可缺省。
* parser - 内容转换类别，可缺省。

范例：

```xml
<Book id="abc" name="API Toturial" />
```
```java
@TagInfo(name = "Book")
public class Book {

    @AttrInfo
    private String id;

    @AttrInfo(name = "name")
    private String bookName;
}
```

### @PropInfo

直接使用原始资料型别的元素，可用得资料型别包括：

* long
* int
* short
* byte
* boolean
* double
* float
* String
* BigDecimal

参数：

* name - 元素名称，可缺省。
* parser - 内容转换类别，可缺省。
* cdata - 内容是否为 CDATA 格式。

范例：

```xml
<Book id="abc" name="API Toturial" />
    <author>Kyle</author>
    <price>100</price>
    <hardback>true</hardback>
</Book>
```
```java
@TagInfo(name = "Book")
public class Book {

    @AttrInfo
    private String id;

    @AttrInfo(name = "name")
    private String bookName;

    @PropInfo(name = "author")
    private String authorName;

    @PropInfo
    private int price;

    @PropInfo
    private boolean hardback;
}
```

### @ContentInfo

元素的内容。此标记使用在同时具有属性与内容的元素上。可用的资料型别包括：

* long
* int
* short
* byte
* boolean
* double
* float
* String
* BigDecimal

参数：

* parser - 内容转换类别，可缺省。
* cdata - 内容是否为 CDATA 格式。

范例：

```xml
<Book id="abc" name="API Toturial" />
    <author>Kyle</author>
    <price>100</price>
    <hardback>true</hardback>
    <release time="2022-10-26">Good</release>
</Book>
```
```java
@TagInfo(name = "Book")
public class Book {

    @AttrInfo
    private String id;

    @AttrInfo(name = "name")
    private String bookName;

    @PropInfo(name = "author")
    private String authorName;

    @PropInfo
    private int price;

    @PropInfo
    private boolean hardback;

    private @TagInfo
    private Release release;
}

@TagInfo
public class Release {

    @AttrInfo
    private String time;

    @ContentInfo
    private String text;
}
```

### @XmlInfo

定义内容为 XML 文字信息的元素。

Properties:

* name - 元素名称，可缺省。

范例：

```xml
<Lib>
    <Books>
        <Book id="abc" name="API Toturial" />
            <author>Kyle</author>
            <price>100</price>
            <hardback>true</hardback>
            <release time="2022-10-26">Good</release>
        </Book>
    </Books>
</Lib>
```
```java
@TagInfo(name = "Lib")
public class Lib {

    @XmlInfo(name = "Books")
    private String books;
}
```

解析 XML 文件后，保存在 `books` 内的文字信息将是
```xml
<Book id="abc" name="API Toturial" /><author>Kyle</author><price>100</price><hardback>true</hardback><release time="2022-10-26">Good</release></Book>
```

### @TagListInfo

定义集合类型的元素。

参数：

* @TagListInfo
    * name - 元素名称，可缺省。
    * elems - 定义集合内元素的信息，以 @TagListElem 阵列记录。
    * inline - 此元素是否为 inline 格式。true 表示元素名称不存在于 XML 中，可缺省。

* @TagListElem
    * name - 元素名称。
    * type - 对应类别。

范例：

1. inline 格式
    ```xml
    <Lib>
        <Book />
        <Book />
        <Book />
    </Lib>
    ```
    ```java
    @TagInfo(name = "Lib")
    public class Lib {

        @TagListInfo(elems = { @TagListElem(name = "Book", type = Book.class) }, inline = true)
        private ArrayList<Book> values;

    }

    @TagInfo
    public class Book {}
    ```

2. 非 inline 格式
    ```xml
    <Lib>
        <Books>
            <Book />
            <Book />
            <Book />
        </Books>
    </Lib>
    ```
    ```java
    @TagInfo(name = "Lib")
    public class Lib {

        @TagListInfo(name = "Books", elems = { @TagListElem(name = "Book", type = Book.class) })
        private ArrayList<Book> books;

    }

3. 集合中的元素名称不相同。
    ```xml
    <Zoo>
        <Animals>
            <Tiger id="0001" />
            <Lion id="0002" />
            <Lion id="0003" />
            <Tiger id="0004" />
            <Lion id="0005" />
        </Animals>
    </Zoo>
    ```
    ```java
    @TagInfo(name = "Zoo")
    public class Zoo {

        @TagListInfo(
            name = "Animals", 
            elems = { 
                @TagListElem(name = "Tiger", type = Tiger.class) 
                @TagListElem(name = "Lion", type = Lion.class) 
            })
        private ArrayList<Animal> animals;

    }

## 内容转换

`@AttrInfo` 、 `@PropInfo` 和 `@Content` 支援内容转换配置。转换配置的类需要实作 `uia.xml.XObjectValue` 界面。

范例：

元素 `result` 在类中定义为 `boolean` 型别，而在 XML 内用 `Y` 或 `N` 保存。 

```xml
<result>Y</result>
```
```java
@PropInfo(parser = BooleanValue.class)
private boolean result;
```
```java
public class BooleanValue implements XObjectValue {

    public Object read(Field f, String text) {
        return "Y".equals(text);
    }

    public String write(Object value) {
        boolean b = (Boolean)value;
        return b ? "Y" : "N";
    }
}
```

## 版权与授权

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
