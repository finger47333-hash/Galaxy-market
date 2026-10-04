package com.example

import com.example.data.AppStoreRepository
import com.example.model.Review
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun repository_hasRetroApps() {
    val repo = AppStoreRepository()
    val apps = repo.apps.value
    assertTrue("Should have apps in repository", apps.size >= 15)

    val tom = apps.find { it.id == "talking_tom" }
    assertNotNull("Talking Tom must exist", tom)

    val flappy = apps.find { it.id == "flappy_bird" }
    assertNotNull("Flappy bird must exist", flappy)

    val vk = apps.find { it.id == "vkontakte_retro" }
    assertNotNull("VK must exist", vk)
  }

  @Test
  fun repository_submitReview_updatesReviews() {
    val repo = AppStoreRepository()
    val initialReviewsCount = repo.apps.value.first { it.id == "talking_tom" }.reviews.size

    val newReview = Review(
        id = "test_rev",
        authorName = "РетроТестер",
        authorAvatarColor = 0xFF4CAF50,
        rating = 5,
        date = "Сегодня",
        comment = "Шедевр 2013 года!",
        helpfulCount = 0
    )
    repo.addReview("talking_tom", newReview)

    val updatedTom = repo.apps.value.first { it.id == "talking_tom" }
    assertEquals(initialReviewsCount + 1, updatedTom.reviews.size)
    assertEquals("РетроТестер", updatedTom.reviews.first().authorName)
  }

  @Test
  fun repository_appsHaveApkDetails() {
    val repo = AppStoreRepository()
    val apps = repo.apps.value
    for (app in apps) {
      assertTrue("App ${app.name} must have .apk filename", app.apkFileName.endsWith(".apk"))
      assertTrue("App ${app.name} must have package name", app.packageName.isNotEmpty())
    }
  }
}
