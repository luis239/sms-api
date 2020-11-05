package com.caliente.ui.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseRequestPin(

	@Json(name="phone")
	val phone: String? = null,

	@Json(name="prefix")
	val prefix: String? = null,

	@Json(name="status")
	val status: String? = null,

	@Json(name="desc")
	val desc: String? = null,

	@Json(name="mcUsed")
	val mcUsed: Boolean? = null,

	@Json(name="eligible")
	val eligible:Boolean? = null
)
