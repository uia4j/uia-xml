uia-xml
===

## Description

Map XML document to objects using StAX API.

[中文](README_CN.md)

## Annotations

The annotations used to define XML document includes

* @TagInfo
* @AttrInfo
* @PropInfo
* @ContentInfo
* @XmlInfo
* @TagListInfo
* @TagListElem

### @TagInfo

Define an element.

Properties:

* name - the element name.

Examples:

```xml
<Book />
```
```java
@TagInfo(name = "Book")
public class Book {}
```

### @AttrInfo

Define an attribute of an element. Value type supports

* long
* int
* short
* byte
* boolean
* double
* float
* String
* BigDecimal

Properties:

* name - the attribute name.
* parser - the value parser.

Examples:

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

Simple element, Value type supports

* long
* int
* short
* byte
* boolean
* double
* float
* String
* BigDecimal

Properties:

* name - the element name.
* parser - the value parser.
* cdata - if the content is CDATA or not.

Examples:

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

Contnet of an element. This will be used when a XML element has attributes and text. Value type supports

* long
* int
* short
* byte
* boolean
* double
* float
* String
* BigDecimal

Properties:

* parser - the value parser.
* cdata - if the content is CDATA or not.

Exammples:

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

The content is XML fommat.

Properties:

* name - the element name.

Exammples:

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

After reading XML document, the text stored in the `books` will be
```xml
<Book id="abc" name="API Toturial" /><author>Kyle</author><price>100</price><hardback>true</hardback><release time="2022-10-26">Good</release></Book>
```

### @TagListInfo

List element.

Properties:

* @TagListInfo
    * name - the element name.
    * elems - definition of sub-elements in the list. Array of @TagListElem.
    * inline - if element exists or not.

* @TagListElem
    * name - the element name.
    * type - the class.

examples:

1. inline style
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
    ```

2. NOT inline style
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

3. list with mulitple types.
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

## Value Parser

`@AttrInfo`, `@PropInfo` and `@Content` support parser configurtion. The parser class needs to implement `uia.xml.XObjectValue` interface.

For example, the type of `result` element in the class is `boolean`, and the XML content is `Y` or `N`. 

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

## Copyright and License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
