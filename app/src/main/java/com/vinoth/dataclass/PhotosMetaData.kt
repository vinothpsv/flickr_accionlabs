package com.vinoth.dataclass

data class PhotosMetaData(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<PhotoResponse>,
)