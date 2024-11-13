package org.bluebridge.configurationProperties_Inject_by_configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "library")
@Configuration  //一般用来声明配置类，可以使用@Component注解替代，不过使用@Configuration注解声明配置类更加语义
public class LibraryConfigInjectByConfiguration {
    private String location;
    private List<Book> books;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    static class Book {
        private String name;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Library{" +
                "location='" + location + '\'' +
                ", books=" + books +
                '}';
    }
}
