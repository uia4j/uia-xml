uia-xml
===

## Description

Map XML document to objects using StAX API.

## Documentation

The annotations used to define XML document includes

* @TagInfo
* @AttrInfo
* @PropInfo
* @ContentInfo
* @TagListInfo
* @TagListElem

### @TagInfo
Use to describle an element.

Properties:

* name - the tag name.

Examples:

```xml
<doc />
```
```java
@TagInfo(name = "doc")
public class Doc {}
```
### @AttrInfo
Use to describe an attribute.


Properties:

* name - the attribute name.

Examples:

```xml
<Doc id="abc" Name="" />
```
```java
@TagInfo
public class Doc {

    @AttrInfo
    private String id;

    @AttrInfo(name = "Name")
    private String name;
}
```

### @PropInfo

Simple element, supporting type includes

* long
* int
* short
* byte
* bool
* double
* float
* String
* BigDecimal

Properties:

* name - the property name.

Examples:

```xml
<Doc id="abc" >
    <Prop1>name</Prop1>
    <prop2>10</prop2>
    <prop3>true</prop3>
</Doc>
```
```java
@TagInfo
public class Doc {

    @AttrInfo
    private String id;

    @PropInfo(name = "Prop1")
    private String prop1;

    @PropInfo
    private int prop2;

    @PropInfo
    private bool prop3;
}
```

### @ContentInfo
Contnet of an element. This will be used when a XML tag has attributes and text.

Properties:

Exammples:

```xml
<Doc id="abc">Text Here</Doc>
```
```java
@TagInfo
public class Doc {

    @AttrInfo
    private String id;

    @ContentInfo
    private String value;
}
```

### @TagListInfo
list elements.

Properties:

* @TagListInfo
    * name - the tag name.
    * elems - definitions of elements in the list. Array of @TagListElem.
    * inline - if tag exists or not.

* @TagListElem
    * name - the tag name.
    * type - the class.

examples:

1. inline
    ```xml
    <Books>
        <Book />
        <Book />
        <Book />
    </Books>
    ```
    ```java
    @TagInfo(name = "Books")
    public class Books {

        @TagListInfo(elems = { @TagListElem(name = "Book", type = Book.class) }, inline = true)
        private ArrayList<Book> values;

    }
    ```

2. list
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


