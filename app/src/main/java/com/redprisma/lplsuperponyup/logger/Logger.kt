package com.redprisma.lplsuperponyup.logger

interface Logger {
    fun e(tag: String, message: String)
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
}