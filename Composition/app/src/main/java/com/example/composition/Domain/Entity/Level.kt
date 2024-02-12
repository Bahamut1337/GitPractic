package com.example.composition.Domain.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
enum class Level : Parcelable {

    TEST,EASY,NORMAL,HARD

}