package me.takehara.domain.youtube

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import me.takehara.domain.youtube.valueobject.Title
import me.takehara.domain.youtube.valueobject.UnvalidatedThumbnails

class UnvalidatedYoutubeVideoTest : FreeSpec({
    "isPublicVideo" - {
        "自身がPublicな動画であればtrue" {
            val public = UnvalidatedYoutubeVideo(mockk(), mockk(), Title("Some video"), mockk(), mockk(), mockk())
            public.isPublicVideo() shouldBe true
        }

        "自身がPrivateな動画であればfalse" {
            val private = UnvalidatedYoutubeVideo(mockk(), mockk(), Title("Private video"), mockk(), mockk(), mockk())
            private.isPublicVideo() shouldBe false
        }

        "自身の動画タイトルがnullであればtrue" {
            val nullTitle = UnvalidatedYoutubeVideo(mockk(), mockk(), null, mockk(), mockk(), mockk())
            nullTitle.isPublicVideo() shouldBe true
        }
    }

    "hasNull" - {
        "1つもnullが無い場合はfalseを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), mockk(), mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe false
        }

        "idがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), mockk(), mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(null, mockk(), mockk(), mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe true
        }

        "videoIdがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), mockk(), mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), null, mockk(), mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe true
        }

        "titleがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), mockk(), mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), null, mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe true
        }

        "descriptionがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), mockk(), mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), null, mockk(), thumbnails)
            target.hasNull() shouldBe true
        }

        "publishedDateがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), mockk(), mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), mockk(), null, thumbnails)
            target.hasNull() shouldBe true
        }

        "thumbnailsがnullであればtrueを返す" {
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), mockk(), mockk(), null)
            target.hasNull() shouldBe true
        }

        "thumbnails.defaultがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(null, mockk(), mockk(), mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe true
        }

        "thumbnails.highがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), null, mockk(), mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe true
        }

        "thumbnails.maxresがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), null, mockk(), mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe true
        }

        "thumbnails.mediumがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), mockk(), null, mockk())
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe true
        }

        "thumbnails.standardがnullであればtrueを返す" {
            val thumbnails = UnvalidatedThumbnails(mockk(), mockk(), mockk(), mockk(), null)
            val target = UnvalidatedYoutubeVideo(mockk(), mockk(), mockk(), mockk(), mockk(), thumbnails)
            target.hasNull() shouldBe true
        }
    }
})
