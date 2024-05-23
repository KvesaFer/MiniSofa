# Kotlin Basics 🚀

## Table of Contents
- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Basic Syntax](#basic-syntax)
    - [Hello World](#hello-world)
- [Variables and Constants](#variables-and-constants)
- [Data Types](#data-types)
- [Control Flow](#control-flow)
    - [If-Else Statements](#if-else-statements)
    - [When Expression](#when-expression-similar-to-switch-statement)
- [Loops](#loops)
    - [For Loop](#for-loop)
    - [While Loop](#while-loop)
- [Functions](#functions)
    - [Declaring Functions](#declaring-functions)
    - [Calling Functions](#calling-functions)
- [Classes and Objects](#classes-and-objects)
    - [Declaring a Class](#declaring-a-class)
    - [Creating Objects](#creating-objects)
    - [Accessing Properties and Methods](#accessing-properties-and-methods)
- [Nullable Types](#nullable-types)
- [Conclusion](#conclusion)

## Introduction

Kotlin is a modern programming language that is expressive, concise, and interoperable with Java. It was developed by JetBrains and officially supported for Android app development by Google. Kotlin offers many features that make it a powerful and enjoyable language to work with.

## Getting Started

To start programming in Kotlin, you need to set up a development environment. You can use IntelliJ IDEA, Android Studio, or any other text editor with Kotlin support. Once you have your environment set up, you can create a new Kotlin project and start writing code.

## Basic Syntax

### Hello World

```kotlin
fun main() {
    println("Hello, World!")
}
```
## Variables and Constants

```kotlin
val greeting = "Hello"
var count = 10
```

## Data Types

Kotlin has several built-in data types, including:

- `Int` for integer numbers
- `Double` for floating-point numbers
- `Boolean` for true/false values
- `String` for text
- `List` for lists of elements
- `Map` for key-value pairs

## Control Flow

### If-Else Statements
```kotlin
val x = 10
val y = 20

if (x > y) {
    println("x is greater than y")
} else {
    println("x is not greater than y")
}
```

### When Expression (Similar to Switch Statement)
```kotlin
val dayOfWeek = 3

val day = when (dayOfWeek) {
    1 -> "Monday"
    2 -> "Tuesday"
    3 -> "Wednesday"
    4 -> "Thursday"
    5 -> "Friday"
    6 -> "Saturday"
    7 -> "Sunday"
    else -> "Invalid day"
}

println("Today is $day")
```
## Loops

### For Loop
```kotlin
for (i in 1..5) {
    println("Count: $i")
}
```
### While Loop
```kotlin
var i = 1
while (i <= 5) {
    println("Count: $i")
    i++
}
```
## Functions

### Declaring Functions

```kotlin
fun add(x: Int, y: Int): Int {
    return x + y
}
```

### Calling Functions

```kotlin
val result = add(3, 5)
println("Result: $result")
```
## Classes and Objects

### Declaring a Class

```kotlin
class Person(val name: String, var age: Int)
```

### Creating Objects

```kotlin
val person = Person("Alice", 30)
```

### Accessing Properties and Methods

```kotlin
println("Name: ${person.name}, Age: ${person.age}")
```
## Nullable Types

In Kotlin, variables are non-nullable by default, meaning they cannot hold null values. If you want a variable to be nullable, you can use the ? operator.

```kotlin
var nullableString: String? = null
```

## Conclusion

This is just a brief introduction to Kotlin basics. There's a lot more to learn, including advanced topics like lambdas, higher-order functions, extension functions, and more. Experiment with the code examples provided here and continue exploring the Kotlin documentation and tutorials to become proficient in Kotlin programming.

## Additional Resources 📚

- [Kotlin Koans](https://play.kotlinlang.org/koans/overview)
- [Kotlin vs Java](https://www.infoworld.com/article/3396141/why-kotlin-eight-features-that-could-convince-java-developers-to-switch.html)
- [Kotlin Android](https://techbeacon.com/app-dev-testing/why-you-should-use-kotlin-android-devel)