plugins {
  alias(libs.plugins.lombok)
  alias(libs.plugins.shadow)
}

repositories {
}

dependencies {
  annotationProcessor(libs.lombok)

  shadow(project(":common:models"))
  implementation(project(":common:models"))

  shadow(libs.avajeinject)
  implementation(libs.avajeinject)
  annotationProcessor(libs.avajeinject.processor)
}

tasks.shadowJar {
  configurations = listOf(project.configurations.shadow.get())

  val pluginVersion: String by project
  val pluginMainClass: String by project

  archiveVersion = pluginVersion
  archiveBaseName = rootProject.name
  archiveClassifier = ""

  enableAutoRelocation = true

  val relocationBase = pluginMainClass.substringBeforeLast('.').replace('.', '/')

  relocationPrefix = "$relocationBase/dependencies"

  mergeServiceFiles()

  duplicatesStrategy = DuplicatesStrategy.WARN
}
