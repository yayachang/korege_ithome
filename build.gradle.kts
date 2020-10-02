import com.soywiz.korge.gradle.*

buildscript {

	repositories {

		mavenLocal()
		maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
		maven { url = uri("https://plugins.gradle.org/m2/") }
		mavenCentral()
		google()
		jcenter()

	}
	dependencies {
		classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:1.15.0.0")

	}
}

apply<KorgeGradlePlugin>()

korge {
	id = "yaya.idv.mygame"
	supportBox2d()

}

dependencies {
	add("commonMainApi", "com.google.code.gson:gson:2.8.6")
}
