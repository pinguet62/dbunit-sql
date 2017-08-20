# DbUnit SQL

[![Dependency Status](https://www.versioneye.com/user/projects/587a72a2de66c8004cdac018/badge.svg?style=flat)](https://www.versioneye.com/user/projects/587a72a2de66c8004cdac018)
[![Known Vulnerabilities](https://snyk.io/test/github/pinguet62/dbunit-sql/badge.svg)](https://snyk.io/test/github/pinguet62/dbunit-sql)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f47a566d60f549c38b4ac4e72e06183d)](https://www.codacy.com/app/pinguet62/dbunit-sql?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pinguet62/dbunit-sql&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/pinguet62/dbunit-sql.svg?branch=master)](https://travis-ci.org/pinguet62/dbunit-sql)
[![codecov.io](https://codecov.io/github/pinguet62/dbunit-sql/coverage.svg?branch=master)](https://codecov.io/github/pinguet62/dbunit-sql?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/pinguet62/dbunit-sql/badge.svg?branch=master)](https://coveralls.io/github/pinguet62/dbunit-sql?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.pinguet62/dbunit-sql/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.pinguet62/dbunit-sql)
[![Javadocs](https://www.javadoc.io/badge/fr.pinguet62/dbunit-sql.svg)](https://www.javadoc.io/doc/fr.pinguet62/dbunit-sql)

SQL dataset support for [DbUnit](http://dbunit.sourceforge.net) and [spring-test-dbunit](https://springtestdbunit.github.io/spring-test-dbunit) extension for *Spring test*.

## Description

Are equivalents:

* SQL dataset:
	
	```sql
	insert into profile (id, key) values ('1st', 'first');
	insert into user (id, email, profile_id) values (1, 'first@domain.org', '1st');
	```

* XML dataset:
	
	```xml
	<dataset>
		<profile id="1st" key="first">
		<user id="1" email="first@domain.org" profile_id="1st">
	</dataset>
	```

## Dependencies

To use SQL support, add this dependency to your `pom.xml`:
```xml
<dependency>
	<groupId>fr.pinguet62</groupId>
	<artifactId>dbunit-sql</artifactId>
	<version>...</version>
</dependency>
```

It's necessary to add **DbUnit** and/or **spring-test-dbunit** dependencies, because this project use `provided` *scope*.

## Usage

### DbUnit (native)

From [Getting Started](http://dbunit.sourceforge.net/howto.html#Step_2:_Extend_a_DBTestCase_class):

```java
// [...]
import fr.pinguet62.dbunit.sql.ext.SqlDataSet;

public class SqlTest extends DBTestCase {
	// [...]

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new SqlDataSet(getClass().getResourceAsStream("/dataset.sql"));
		// return new SqlDataSet(getClass().getResourceAsStream("insert into profile (id, key) values ('1st', 'first');"));
	}
}
```

### spring-test-dbunit (extension)

```java
// [...]
import fr.pinguet62.dbunit.sql.springtest.SqlDataSetLoader;

// [...]
@TestExecutionListeners({ /*...,*/ TransactionDbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = SqlDataSetLoader.class)
@DatabaseSetup("/dataset.sql")
public class SqlTest {
	// [...]
}
```