package com.example.urrency.retrofit.cbr

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Record")
data class Record(
    @field:Attribute(name = "Date")
    var date: String = "",

    @field:Attribute(name = "Id")
    var id: String = "",

    @field:Element(name = "Nominal")
    var nominal: Int = 0,

    @field:Element(name = "Value")
    var value: String = "",

    @field:Element(name = "VunitRate")
    var vunitRate: String = ""
)