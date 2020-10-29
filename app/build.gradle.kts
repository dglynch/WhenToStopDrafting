plugins {
    id("com.dglynch.whentostopdrafting.java-application-conventions")
}

dependencies {
    implementation(project(":log"))
}

application {
    mainClass.set("com.dglynch.whentostopdrafting.App")
}
