package com.example.directorioapp

import com.example.directorioapp.data.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoritesTest {
    @Test
    fun testUserCreationAndProperties() {
        val user = User(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "Sincere@april.biz",
            address = null,
            phone = "1-770-736-8031 x56442",
            website = "hildegard.org",
            company = null
        )
        assertEquals(1, user.id)
        assertEquals("Leanne Graham", user.name)
        assertEquals("Sincere@april.biz", user.email)
    }
}
