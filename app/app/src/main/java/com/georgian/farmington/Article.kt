package com.georgian.farmington

import com.google.firebase.Timestamp


data class Article(var title:String?= null, var description:String?= null,
 var timestamp: Timestamp ?= null, var summary: String ?= null) {

}