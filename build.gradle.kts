plugins {
    java
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.diffplug.spotless") version("6.11.0")
    id("org.asciidoctor.jvm.convert") version ("3.3.2")
}

group = "com.ko"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }

}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
    implementation("org.modelmapper:modelmapper:2.4.2")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

//    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    java {
        // 사용하지 않는 import 제거
        removeUnusedImports()
        // 구글 자바 포맷 적용
        googleJavaFormat()
        indentWithTabs(2)
        indentWithSpaces(4)
        // 공백 제거
        trimTrailingWhitespace()
        // 끝부분 New Line 처리
        endWithNewline()
    }
}

val asciidoctorExt: Configuration by configurations.creating
dependencies {
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

val snippetsDir by extra { file("build/generated-snippets") }
tasks {
    test {
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        configurations(asciidoctorExt.name)
        dependsOn(test)
        doLast {
            copy {
                from("build/docs/asciidoc")
                into("src/main/resources/static/docs")
            }
        }
    }

    build {
        dependsOn(asciidoctor)
    }
}

