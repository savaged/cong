dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	
    development {
		dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:hsqldb:mem:devDb"
		}
	}
    /*
    development {
        dataSource {
            dataClassName = "org.apache.derby.jdbc.EmbeddedDriver"
            dbCreate = "update"
            url = "jdbc:derby:devDB;create=true"
            username = ""
            password = ""
        }
    }
    */
	test {
		dataSource {
			dbCreate = "create-drop"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
	prodcopy {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:file:/Users/davidsavage/savaged.info/cong/reports/cong/testDb/congDb;shutdown=true"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
                        url = "jdbc:hsqldb:hsql://localhost/congdb"
		}
	}
}
