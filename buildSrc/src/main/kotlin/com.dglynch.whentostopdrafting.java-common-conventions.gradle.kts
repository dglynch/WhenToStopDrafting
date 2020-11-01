plugins {
    java
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains:annotations:20.1.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("com.github.stefanbirkner:system-lambda:1.1.1")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:3.5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}
