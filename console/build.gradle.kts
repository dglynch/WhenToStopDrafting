plugins {
    id("com.dglynch.whentostopdrafting.java-application-conventions")
}

dependencies {
    implementation(project(":data"))
}

application {
    mainClass.set("com.dglynch.whentostopdrafting.ConsoleApplication")
}
