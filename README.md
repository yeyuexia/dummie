# Welcome to Dummie [![Build Status](https://travis-ci.org/dummie-java/dummie.svg?branch=master)](https://travis-ci.org/dummie-java/dummie) [![Maven Central](https://img.shields.io/maven-central/v/io.github.dummie-java/dummie.svg)](https://search.maven.org/search?q=g:io.github.dummie-java%20AND%20a:dummie)

### Overview

**Dummie** is a simple data generator for preparing test datas, it provides an easy way to construct complicted data class as below:

```
TargetClass instance = Dummie.create(TargetClass.class);
```

### Usage

* Add Dependency
```groovy
compile "io.github.dummie-java:dummie:${latestVersion}"
```

##### Basic usage

When you writing test code, you may want to create multi complicted datas like:

```
public User People {
	private Long id;
	private String name;
	private Integer age;
	...
	//getter & setter
}

public class Employee extend User {
	private Long id;
	private String mobile;
	private String email;
	private List<Asset> assets;
	...
	//getter & setter
}

public class Asset {
	private Long id;
	private String name;
	private String desc;
	...
	//getter & setter
}
```

The only thing you want do is create an Employee instance with fullfilled datas and deliver to other method. With **Dummie**, you can use simple one line code to get what your want:

```
Employee dummieEmployee = Dummie.create(Employee.class);
```

Maybe you want some fields special in the dummy Employee.

##### Override types / fields
* Suppose in next test, we want all asset name has same value, so we can use `override` method:

```
Employee dummieEmployee = Dummie.prepare().override("desc", "value").create(Employee.class);
```

so all fields named **desc** and type is string should be filled value **"value"**, the type based on `override` second parameter type.

* If you want all fields with the type `Long` to be set 100L, you can:

```
Employee dummieEmployee = Dummie.prepare().override(Long.class, 100L).create(Employee.class);
```

##### Use regex expression override fields
* We can use regex expression to override fields like:
```
Employee dummieEmployee = Dummie.prepare().override(".*Time", new ZonedDateTime()).create(Employee.class);

```

##### Random fields
* Suppose we want all **id** would be random value, we can use `random` method:

```
Employee dummieEmployee = Dummie.prepare().random("id").create(Employee.class);
```

so all fields named **id** would filled random value.

* Like `override`, random can also special a kind of type:

```
Employee dummieEmployee = Dummie.prepare().random(String.class).create(Employee.class);
```

##### Add custom FieldValueGenerator
```
Employee dummieEmployee = Dummie.prepare()
  .valueGenerator(someGenerator)
	.create(Employee.class);
```

##### Change generate strategy

**Dummie** default use static value for data generate. If you want all fields value use random value generator, you should set GenerationStrategy:
```
Employee dummieEmployee = Dummie.withStrategy(GenerationStrategy.RANDOM).create(Employee.class);
```

##### You can also override DataCache and DataGenerator by yourself
```
Configuration configuration = new Configuration(cycleLogic, strategy, dataCache, dataGenerator);
Employee dummieEmployee = Dummie.withConfiguration(configuration).create(Employee.class);
```

### Advance usage
If the target class fields have cycle used, **Dummie** has two logic to deal with.

By default, **Dummie** cache field value, when find the same field, **Dummie** would return the cache value. 

And **Dummie** also support generate by cycle deep, if you want use you can set

```
Employee dummieEmployee = Dummie.cycleLogic(CycleLogic.LEVEL)
	.withFloor(limit) // Optional, set cycle deep level, default value is 2.
	.create(Employee.class);
```
