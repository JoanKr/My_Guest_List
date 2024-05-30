package com.example.mytodo.data

import java.util.UUID

object Guests {
    private val guestsMap: MutableMap<String, Guest> = mutableMapOf()

    init {
        //kilka osób na start
        addGuest(Guest(UUID.randomUUID().toString(), "Joanna Krajewska", Category.FRIEND, "Niekarzyn 1/1", 22))
        addGuest(Guest(UUID.randomUUID().toString(), "Jan Kowalski", Category.FAMILY, "Sulechow ul. Boczna 4", 31))
        addGuest(Guest(UUID.randomUUID().toString(), "Emilia Nowak", Category.COMPANION, "Poznan ul. Ptasia 21b", 18))
    }

    fun addGuest(guest: Guest): Boolean {
        if (guestsMap.containsKey(guest.id)) {
            return false
        }
        guestsMap[guest.id] = guest
        return true
    }

    fun getGuest(guestId: String): Guest? {
        return guestsMap[guestId]
    }

    fun updateGuest(guestId: String, newGuest: Guest): Boolean {
        if (!guestsMap.containsKey(guestId)) {
            return false
        }
        guestsMap[guestId] = newGuest
        return true
    }

    fun removeGuest(guestId: String): Boolean {
        if (guestsMap.containsKey(guestId)) {
            guestsMap.remove(guestId)
            return true
        }
        return false
    }

    fun getAllGuests(): List<Guest> {
        return guestsMap.values.toList()
    }
}
