package com.alejandro.classes

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation


data class UserWithFestivals(
 @Embedded val user: User,
 @Relation(
  parentColumn = "id_user",
  entityColumn = "id_festival",
  associateBy = Junction(Ticket::class)
 )
 val festivals: List<Festival>
)