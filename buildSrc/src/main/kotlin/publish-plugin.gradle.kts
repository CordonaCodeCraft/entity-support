import java.net.URI

plugins {
	`java-library`
	`maven-publish`
}

group = "tech.cordona"
version = "1.3-SNAPSHOT"

java {
	withJavadocJar()
	withSourcesJar()
}

val cordonaRepoWriteUrl: String by project
val publication = "RelationalEntitySupport"

publishing {
	repositories {
		maven {
			url = URI(cordonaRepoWriteUrl)
		}
	}
	publications {
		create<MavenPublication>(publication) {
			artifactId = "relational-entity-support"
			from(components["java"])
			versionMapping {
				usage("java-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
			pom {
				name.set("Relational Entity Support")
				description.set("A concise description of my library")
				url.set("http://www.example.com/library")
				properties.set(
					mapOf(
						"myProp" to "value",
						"prop.with.dots" to "anotherValue"
					)
				)
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						id.set("johnd")
						name.set("John Doe")
						email.set("john.doe@example.com")
					}
				}
				scm {
					connection.set("scm:git:git://example.com/my-library.git")
					developerConnection.set("scm:git:ssh://example.com/my-library.git")
					url.set("http://example.com/my-library/")
				}
			}
		}
	}
	tasks.withType<GenerateMavenPom>().all {
		doLast {
			val file = File("$buildDir/publications/$publication/pom-default.xml")
			var text = file.readText()
			val regex =
				"(?s)(<dependencyManagement>.+?<dependencies>)(.+?)(</dependencies>.+?</dependencyManagement>)".toRegex()
			val matcher = regex.find(text)
			if (matcher != null) {
				text = regex.replaceFirst(text, "")
				val firstDeps = matcher.groups[2]!!.value
				text = regex.replaceFirst(text, "$1$2$firstDeps$3")
			}
			file.writeText(text)
		}
	}
}





