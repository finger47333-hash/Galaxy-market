package com.example.data

import com.example.model.AppCategory
import com.example.model.AppItem
import com.example.model.DownloadState
import com.example.model.DownloadStatus
import com.example.model.Review
import com.example.model.ScreenshotItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppStoreRepository {

    private val _apps = MutableStateFlow<List<AppItem>>(initialApps())
    val apps: StateFlow<List<AppItem>> = _apps.asStateFlow()

    private val _searchHistory = MutableStateFlow(
        listOf("Говорящий Том", "Фонарик", "ВКонтакте", "Doodle Jump", "Flappy Bird", "Fruit Ninja", "NotPipe")
    )
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()

    fun updateDownloadState(appId: String, downloadState: DownloadState) {
        _apps.update { list ->
            list.map { app ->
                if (app.id == appId) {
                    val installed = downloadState.status == DownloadStatus.INSTALLED
                    app.copy(
                        downloadState = downloadState,
                        isInstalled = if (installed) true else app.isInstalled
                    )
                } else app
            }
        }
    }

    fun uninstallApp(appId: String) {
        _apps.update { list ->
            list.map { app ->
                if (app.id == appId) {
                    app.copy(
                        isInstalled = false,
                        downloadState = DownloadState(DownloadStatus.NOT_INSTALLED, 0f, "")
                    )
                } else app
            }
        }
    }

    fun addReview(appId: String, newReview: Review) {
        _apps.update { list ->
            list.map { app ->
                if (app.id == appId) {
                    val updatedReviews = listOf(newReview) + app.reviews
                    val newAverage = ((app.rating * app.reviews.size + newReview.rating) / (app.reviews.size + 1))
                    val rounded = Math.round(newAverage * 10f) / 10f
                    app.copy(
                        reviews = updatedReviews,
                        rating = rounded.coerceIn(1.0f, 5.0f)
                    )
                } else app
            }
        }
    }

    fun addSearchQuery(query: String) {
        if (query.isBlank()) return
        _searchHistory.update { list ->
            (listOf(query.trim()) + list.filter { it.lowercase() != query.trim().lowercase() }).take(8)
        }
    }

    fun clearSearchHistory() {
        _searchHistory.value = emptyList()
    }

    private fun initialApps(): List<AppItem> = listOf(
        AppItem(
            id = "talking_tom",
            name = "Говорящий Том (Talking Tom)",
            developer = "Outfit7 Limited",
            category = AppCategory.GAMES,
            packageName = "com.outfit7.talkingtom",
            rating = 4.8f,
            reviewsCount = "4 850 120",
            downloadsCount = "500 000 000+",
            sizeText = "14,8 МБ",
            androidVersion = "Android 2.1 и выше",
            shortDescription = "Легендарный кот, который повторяет ваши слова смешным голосом!",
            fullDescription = "Говорящий Том — это ваш домашний кот, который реагирует на ваши прикосновения и повторяет все ваши слова забавным писклявым голосом!\n\n★ Погладьте Тома — и он замурлычет!\n★ Ткните его в живот или лапу — Том комично отреагирует!\n★ Налейте ему стакан молока.\n★ Официальный оригинальный APK пакет!",
            whatsNew = "Версия 2.0: Оптимизация для процессоров ARMv6 и ARMv7.",
            version = "v2.0.1",
            updatedDate = "15 сентября 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 1,
            primaryColor = 0xFF78909C,
            secondaryColor = 0xFF455A64,
            iconSymbol = "cat",
            apkFileName = "TalkingTom_v2.0.1.apk",
            apkDownloadUrl = "https://archive.org/download/talking-tom-cat-2-v-2.0.1/Talking%20Tom%20Cat%202_v2.0.1.apk",
            screenshots = listOf(
                ScreenshotItem("Том слушает вас", "Говорите в микрофон смартфона", listOf(0xFF78909C, 0xFF37474F), "mic"),
                ScreenshotItem("Миска молока", "Угостите кота парным молоком", listOf(0xFF81D4FA, 0xFF0288D1), "local_cafe")
            ),
            reviews = listOf(
                Review("rev_t1", "Дима Galaxy Ace", 0xFF4CAF50, 5, "12 октября 2013 г.", "Самая лучшая игра! Младший брат смеется без остановки. На Android 2.3.6 летает!", 340),
                Review("rev_t2", "SuperGamer2013", 0xFF2196F3, 5, "8 октября 2013 г.", "Помню как все в школе скачивали Тома по блютузу! Ностальгия просто зашкаливает.", 215)
            )
        ),
        AppItem(
            id = "notpipe_tubemate",
            name = "NotPipe / NewPipe (Загрузчик APK)",
            developer = "Team NewPipe",
            category = AppCategory.MEDIA,
            packageName = "org.schabi.newpipe",
            rating = 4.9f,
            reviewsCount = "2 850 000",
            downloadsCount = "100 000 000+",
            sizeText = "11,2 МБ",
            androidVersion = "Android 4.4 и выше",
            shortDescription = "Легендарный открытый клиент без рекламы со скачиванием видео и аудио на SD-карту!",
            fullDescription = "Настоящий полноценный APK загрузчик видео и музыки!\n\n★ Скачивайте любые видеоролики в 3GP, MP4 720p/1080p и MP3!\n★ Фоновое воспроизведение с выключенным экраном.\n★ Режим всплывающего окна (Picture-in-Picture).\n★ Никакой рекламы и полная приватность без Google сервисов!\n★ Официальный релиз с GitHub.",
            whatsNew = "Быстрая многопоточная загрузка на SD-карту и поддержка плейлистов.",
            version = "v0.27.0",
            updatedDate = "Вчера",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 2,
            primaryColor = 0xFFE53935,
            secondaryColor = 0xFFB71C1C,
            iconSymbol = "download",
            apkFileName = "NewPipe_v0.27.0.apk",
            apkDownloadUrl = "https://github.com/TeamNewPipe/NewPipe/releases/download/v0.27.0/NewPipe_v0.27.0.apk",
            screenshots = listOf(
                ScreenshotItem("Скачивание в MP4/MP3", "Выбор любого качества прямо на телефон", listOf(0xFFE53935, 0xFF8E0000), "video_settings"),
                ScreenshotItem("Фоновый плеер", "Слушайте подкасты и музыку", listOf(0xFFFF7043, 0xFFD84315), "headset")
            ),
            reviews = listOf(
                Review("rev_np1", "Школьник_С_Флешкой", 0xFFE53935, 5, "5 октября 2013 г.", "Скачал все серии любимого сериала на уроки. Качество супер и весит мало!", 380),
                Review("rev_np2", "AndroidFan", 0xFF43A047, 5, "21 сентября 2013 г.", "Реальный рабочий APK без рекламы. Огромное спасибо разработчикам!", 290)
            )
        ),
        AppItem(
            id = "talking_ben",
            name = "Говорящий Бен (Talking Ben the Dog)",
            developer = "Outfit7 Limited",
            category = AppCategory.GAMES,
            packageName = "com.outfit7.talkingben",
            rating = 4.7f,
            reviewsCount = "2 140 000",
            downloadsCount = "100 000 000+",
            sizeText = "16,2 МБ",
            androidVersion = "Android 2.3 и выше",
            shortDescription = "Пес-профессор химии читает газету и смешивает взрывные зелья!",
            fullDescription = "Бен — вышедший на пенсию профессор химии. Проводите опыты в лаборатории!\n\n★ Позвоните Бену по старому кнопочному телефону — он скажет 'Yes? No? Ho-ho-ho!'\n★ Смешивайте пробирки разного цвета и устраивайте веселые взрывы!",
            whatsNew = "Исправлены вылеты на процессорах ARMv6. Добавлен новый зеленый реактив!",
            version = "v1.4.2",
            updatedDate = "3 октября 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 3,
            primaryColor = 0xFF8D6E63,
            secondaryColor = 0xFF4E342E,
            iconSymbol = "dog",
            apkFileName = "TalkingBen_v1.4.2.apk",
            apkDownloadUrl = "https://archive.org/download/talking-ben-dog/TalkingBen.apk",
            screenshots = listOf(
                ScreenshotItem("Лаборатория Бена", "Смешивайте химикаты в пробирках", listOf(0xFF8D6E63, 0xFF3E2723), "science"),
                ScreenshotItem("Телефонный звонок", "Yes? No? Ho-ho-ho!", listOf(0xFF4CAF50, 0xFF2E7D32), "phone")
            ),
            reviews = listOf(
                Review("rev_b1", "Химик 7-Б", 0xFF8D6E63, 5, "5 октября 2013 г.", "Бен просто легенда! Звоню ему каждый день спросить уроки))", 188)
            )
        ),
        AppItem(
            id = "fruit_ninja",
            name = "Фрукт Ниндзя (Fruit Ninja)",
            developer = "Halfbrick Studios",
            category = AppCategory.GAMES,
            packageName = "com.halfbrick.fruitninja",
            rating = 4.9f,
            reviewsCount = "5 320 000",
            downloadsCount = "500 000 000+",
            sizeText = "28,5 МБ",
            androidVersion = "Android 2.3 и выше",
            shortDescription = "Разрезайте сочные арбузы пальцем и остерегайтесь коварных бомб!",
            fullDescription = "Проведите пальцем по экрану, чтобы сочно разрезать фрукты, как настоящий ниндзя!\n\n★ Классический, Дзен и Аркадный режимы!\n★ Нарезайте комбо из 3+ фруктов одновременно!\n★ Мудрый Сенсей дает советы после каждого раунда.",
            whatsNew = "Добавлен ледяной клинок и фоновая доска со смыслом Дзен.",
            version = "v1.8.6",
            updatedDate = "22 сентября 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 4,
            primaryColor = 0xFFEF5350,
            secondaryColor = 0xFFC62828,
            iconSymbol = "ninja",
            apkFileName = "FruitNinja_v1.8.6.apk",
            apkDownloadUrl = "https://archive.org/download/fruit-ninja-classic/FruitNinja.apk",
            screenshots = listOf(
                ScreenshotItem("Сочные комбо", "Разрезайте 3 фрукта одним свайпом", listOf(0xFFEF5350, 0xFFB71C1C), "flash_on")
            ),
            reviews = listOf(
                Review("rev_fn1", "Ниндзя-Михаил", 0xFFE53935, 5, "11 октября 2013 г.", "Пальцы горят после аркадного режима! Набрал 840 очков!", 512)
            )
        ),
        AppItem(
            id = "doodle_jump",
            name = "Doodle Jump (Дудл Джамп)",
            developer = "Lima Sky LLC",
            category = AppCategory.GAMES,
            packageName = "com.limasky.doodlejump",
            rating = 4.9f,
            reviewsCount = "3 410 000",
            downloadsCount = "100 000 000+",
            sizeText = "11,4 МБ",
            androidVersion = "Android 1.6 и выше",
            shortDescription = "Прыгайте по зеленым платформам все выше при помощи акселерометра!",
            fullDescription = "Наклоняйте телефон влево и вправо, чтобы направлять Дудлера по платформам в космос!\n\n★ Пружины, батуты, джетпаки и летающие монстры!\n★ Остерегайтесь коричневых сломанных ступеней!\n★ Легендарная тетрадь в клеточку!",
            whatsNew = "Турнир рекордов! Добавлена космическая тема.",
            version = "v3.1.2",
            updatedDate = "19 августа 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 5,
            primaryColor = 0xFF8BC34A,
            secondaryColor = 0xFF558B2F,
            iconSymbol = "jump",
            apkFileName = "DoodleJump_v3.1.2.apk",
            apkDownloadUrl = "https://archive.org/download/doodle-jump-deluxe/DoodleJump.apk",
            screenshots = listOf(
                ScreenshotItem("Вверх по платформам", "Наклоняйте телефон для прыжков", listOf(0xFF8BC34A, 0xFF33691E), "terrain")
            ),
            reviews = listOf(
                Review("rev_dj1", "Артем_Прыгун", 0xFF8BC34A, 5, "4 октября 2013 г.", "Рекорд 64 210 очков! Шедевр на все времена.", 410)
            )
        ),
        AppItem(
            id = "flappy_bird",
            name = "Flappy Bird (Оригинал APK)",
            developer = ".GEARS Studios",
            category = AppCategory.GAMES,
            packageName = "com.dotgears.flappybird",
            rating = 4.6f,
            reviewsCount = "6 120 000",
            downloadsCount = "100 000 000+",
            sizeText = "890 КБ",
            androidVersion = "Android 2.2 и выше",
            shortDescription = "Легендарная оригинальная желтая птичка и зеленые трубы из Марио!",
            fullDescription = "Тапайте по экрану, чтобы птичка взмахивала крыльями и пролетала через узкие проемы между зелеными трубами!\n\n★ Настоящий оригинальный файл игры 2013 года!\n★ Вес всего 890 Килобайт!\n★ Попробуйте набрать хотя бы 10 очков!",
            whatsNew = "Оригинальный APK от автора Донг Нгуена.",
            version = "v1.2",
            updatedDate = "10 мая 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 6,
            primaryColor = 0xFFFFEB3B,
            secondaryColor = 0xFFFBC02D,
            iconSymbol = "bird",
            apkFileName = "FlappyBird_v1.2.apk",
            apkDownloadUrl = "https://archive.org/download/flappy-bird-original/FlappyBird.apk",
            screenshots = listOf(
                ScreenshotItem("Тапайте по экрану", "Держите птичку в воздухе", listOf(0xFF4CAF50, 0xFF2E7D32), "touch_app")
            ),
            reviews = listOf(
                Review("rev_fb1", "НервыНаПределе", 0xFFF44336, 5, "14 октября 2013 г.", "Мой рекорд 21 очко! Оторваться невозможно!!", 890)
            )
        ),
        AppItem(
            id = "vkontakte_retro",
            name = "ВКонтакте (VK 3.0 Holo APK)",
            developer = "VK.com",
            category = AppCategory.SOCIAL,
            packageName = "com.vkontakte.android",
            rating = 4.9f,
            reviewsCount = "3 890 000",
            downloadsCount = "100 000 000+",
            sizeText = "8,4 МБ",
            androidVersion = "Android 2.3 и выше",
            shortDescription = "Классический синий клиент с бесплатной музыкой в фоне и старой стеной!",
            fullDescription = "Легендарный клиент ВКонтакте эпохи Павла Дурова!\n\n★ Музыка ВКонтакте БЕЗ ограничений и рекламы!\n★ Кэширование треков прямо на карту памяти microSD.\n★ Диалоги со смайликами-колобками.\n★ Дуров, верни стену!",
            whatsNew = "Версия 3.0 Holo: Боковое меню и кэш аудиозаписей.",
            version = "v3.0.4",
            updatedDate = "28 августа 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 7,
            primaryColor = 0xFF45668E,
            secondaryColor = 0xFF27436B,
            iconSymbol = "vk",
            apkFileName = "VKontakte_v3.0.4.apk",
            apkDownloadUrl = "https://archive.org/download/vk-retro-3/VKontakte.apk",
            screenshots = listOf(
                ScreenshotItem("Музыка без лимита", "Кэшируйте треки в дорогу", listOf(0xFF45668E, 0xFF1E3557), "music_note")
            ),
            reviews = listOf(
                Review("rev_vk1", "Павел Д.", 0xFF45668E, 5, "10 октября 2013 г.", "Настоящий контакт! Музыка играет при выключенном экране!", 1240)
            )
        ),
        AppItem(
            id = "tiny_flashlight",
            name = "Фонарик LED (Tiny Flashlight APK)",
            developer = "Nikolay Ananiev",
            category = AppCategory.TOOLS,
            packageName = "dev.flashlight.classic",
            rating = 4.9f,
            reviewsCount = "4 120 000",
            downloadsCount = "500 000 000+",
            sizeText = "1,8 МБ",
            androidVersion = "Android 1.5 и выше",
            shortDescription = "Самый скачиваемый фонарик со стробоскопом, полицией и азбукой Морзе!",
            fullDescription = "Превратите вспышку камеры вашего телефона в яркий прожектор!\n\n★ Реалистичный тумблер-выключатель со щелчком.\n★ Стробоскоп с регулятором частоты.\n★ Полицейская сирена и сигналы SOS азбукой Морзе!",
            whatsNew = "Виджет 1х1 на рабочий стол с мгновенным включением.",
            version = "v5.0.2",
            updatedDate = "18 июля 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 8,
            primaryColor = 0xFFFFA000,
            secondaryColor = 0xFFFF6F00,
            iconSymbol = "flashlight",
            apkFileName = "TinyFlashlight_v5.0.apk",
            apkDownloadUrl = "https://archive.org/download/tiny-flashlight-led/TinyFlashlight.apk",
            screenshots = listOf(
                ScreenshotItem("Реалистичный тумблер", "Включение вспышки камеры", listOf(0xFFFFA000, 0xFFE65100), "flash_on")
            ),
            reviews = listOf(
                Review("rev_fl1", "Сергей Мастер", 0xFFFFA000, 5, "7 октября 2013 г.", "Всегда выручает в темноте. Щелчок тумблера бесподобен!", 430)
            )
        ),
        AppItem(
            id = "angry_birds",
            name = "Angry Birds Classic (APK)",
            developer = "Rovio Mobile Ltd.",
            category = AppCategory.GAMES,
            packageName = "com.rovio.angrybirds",
            rating = 4.8f,
            reviewsCount = "7 890 000",
            downloadsCount = "500 000 000+",
            sizeText = "34,2 МБ",
            androidVersion = "Android 2.3 и выше",
            shortDescription = "Запускайте птиц из рогатки по коварным зеленым свиньям!",
            fullDescription = "Отомстите жадным зеленым свиньям! Ускорение Чака, взрывы Бомбы и раскол синих птенцов на 300+ уровнях!",
            whatsNew = "Эпизод 'Red's Mighty Feathers'.",
            version = "v3.3.0",
            updatedDate = "14 августа 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 9,
            primaryColor = 0xFFD32F2F,
            secondaryColor = 0xFF9A0007,
            iconSymbol = "angry_bird",
            apkFileName = "AngryBirds_v3.3.0.apk",
            apkDownloadUrl = "https://archive.org/download/angry-birds-classic-apk/AngryBirds.apk",
            screenshots = listOf(
                ScreenshotItem("Прицеливание из рогатки", "Учитывайте угол и натяжение", listOf(0xFFD32F2F, 0xFF7F0000), "sports")
            ),
            reviews = listOf(
                Review("rev_ab1", "Птицебоец", 0xFFD32F2F, 5, "9 октября 2013 г.", "Прошел все эпизоды на три звезды!", 670)
            )
        ),
        AppItem(
            id = "subway_surfers",
            name = "Subway Surfers (World Tour APK)",
            developer = "Kiloo",
            category = AppCategory.GAMES,
            packageName = "com.kiloo.subwaysurf",
            rating = 4.9f,
            reviewsCount = "12 400 000",
            downloadsCount = "1 000 000 000+",
            sizeText = "29,4 МБ",
            androidVersion = "Android 2.3.3 и выше",
            shortDescription = "Мчитесь по путям от злого инспектора и его пса, собирая монеты!",
            fullDescription = "Уворачивайтесь от поездов на летающих ховербордах с джетпаком и супер-ботинками!",
            whatsNew = "World Tour: Москва! Снежные пути столицы.",
            version = "v1.16.0",
            updatedDate = "28 ноября 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 10,
            primaryColor = 0xFF0288D1,
            secondaryColor = 0xFF01579B,
            iconSymbol = "subway",
            apkFileName = "SubwaySurfers_v1.16.apk",
            apkDownloadUrl = "https://archive.org/download/subway-surfers-world-tour/SubwaySurfers.apk",
            screenshots = listOf(
                ScreenshotItem("Крыши поездов", "Прыгайте по вагонам на полной скорости", listOf(0xFF0288D1, 0xFF002F6C), "train")
            ),
            reviews = listOf(
                Review("rev_ss1", "JakeRunner", 0xFF0288D1, 5, "12 октября 2013 г.", "Лучший раннер на Android!", 840)
            )
        ),
        AppItem(
            id = "hill_climb_racing",
            name = "Hill Climb Racing (APK)",
            developer = "Fingersoft",
            category = AppCategory.GAMES,
            packageName = "com.fingersoft.hillclimb",
            rating = 4.8f,
            reviewsCount = "8 950 000",
            downloadsCount = "500 000 000+",
            sizeText = "15,6 МБ",
            androidVersion = "Android 2.2 и выше",
            shortDescription = "Билл Ньютон покоряет горы, Луну и пустыню на красном джипе!",
            fullDescription = "Джип, Мотоцикл, Монстр-трак, Танк и Луноход! Прокачивайте двигатель и подвеску 4WD!",
            whatsNew = "Новая локация: Ядерная станция.",
            version = "v1.14.0",
            updatedDate = "19 октября 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 11,
            primaryColor = 0xFFC62828,
            secondaryColor = 0xFF8E0000,
            iconSymbol = "hill_climb",
            apkFileName = "HillClimbRacing_v1.14.apk",
            apkDownloadUrl = "https://archive.org/download/hill-climb-racing-apk/HillClimb.apk",
            screenshots = listOf(
                ScreenshotItem("Сальто на Луне", "Невесомость дает рекордные бонусы", listOf(0xFF37474F, 0xFF212121), "nightlight_round")
            ),
            reviews = listOf(
                Review("rev_hc1", "Тракторист2013", 0xFFC62828, 5, "6 октября 2013 г.", "На луне на танке фармил монеты неделями!", 520)
            )
        ),
        AppItem(
            id = "cut_the_rope",
            name = "Cut the Rope (Ам Ням APK)",
            developer = "ZeptoLab",
            category = AppCategory.GAMES,
            packageName = "com.zeptolab.ctr.game",
            rating = 4.9f,
            reviewsCount = "3 900 000",
            downloadsCount = "100 000 000+",
            sizeText = "31,0 МБ",
            androidVersion = "Android 2.2 и выше",
            shortDescription = "Перерезайте веревки и кормите милого зеленого монстрика леденцами!",
            fullDescription = "Перерезайте веревки, лопайте мыльные пузыри и кормите сладостями Ам Няма!",
            whatsNew = "Новая коробка сыра и уровни с мышами.",
            version = "v2.3.4",
            updatedDate = "25 июля 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 12,
            primaryColor = 0xFF66BB6A,
            secondaryColor = 0xFF2E7D32,
            iconSymbol = "candy",
            apkFileName = "CutTheRope_v2.3.4.apk",
            apkDownloadUrl = "https://archive.org/download/cut-the-rope-apk/CutTheRope.apk",
            screenshots = listOf(
                ScreenshotItem("Кормите Ам Няма", "Доставьте леденец прямо в рот", listOf(0xFF66BB6A, 0xFF1B5E20), "restaurant")
            ),
            reviews = listOf(
                Review("rev_ctr1", "Сладкоежка", 0xFF66BB6A, 5, "28 сентября 2013 г.", "Ам Ням такой милашка!", 490)
            )
        ),
        AppItem(
            id = "opera_mini",
            name = "Opera Mini (сжатие трафика APK)",
            developer = "Opera Software ASA",
            category = AppCategory.TOOLS,
            packageName = "com.opera.mini.android",
            rating = 4.8f,
            reviewsCount = "6 500 000",
            downloadsCount = "500 000 000+",
            sizeText = "2,4 МБ",
            androidVersion = "Android 1.5 и выше",
            shortDescription = "Экономия мобильного интернета до 90% благодаря серверам сжатия!",
            fullDescription = "Самый быстрый браузер в мире для сетей 2G и 3G с экспресс-панелью Speed Dial!",
            whatsNew = "Улучшен рендеринг тяжелых сайтов.",
            version = "v7.5.3",
            updatedDate = "9 сентября 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 13,
            primaryColor = 0xFFC62828,
            secondaryColor = 0xFF8E0000,
            iconSymbol = "opera",
            apkFileName = "OperaMini_v7.5.3.apk",
            apkDownloadUrl = "https://archive.org/download/opera-mini-7.5/OperaMini.apk",
            screenshots = listOf(
                ScreenshotItem("Сжатие до 90%", "Экономьте мегабайты на тарифе", listOf(0xFFC62828, 0xFF490000), "speed")
            ),
            reviews = listOf(
                Review("rev_op1", "ЭкономныйСтудент", 0xFFC62828, 5, "4 октября 2013 г.", "На 50 рублях сижу весь месяц в интернете!", 720)
            )
        ),
        AppItem(
            id = "gravity_defied",
            name = "Gravity Defied (Мототриал APK)",
            developer = "Codebrew Mobile",
            category = AppCategory.GAMES,
            packageName = "org.retro.gravitydefied",
            rating = 4.9f,
            reviewsCount = "2 800 000",
            downloadsCount = "50 000 000+",
            sizeText = "450 КБ",
            androidVersion = "Android 1.5 и выше",
            shortDescription = "Культовый физический мототриал на тонких векторных линиях!",
            fullDescription = "Легенда с кнопочных телефонов Siemens и Sony Ericsson: честная векторная физика 100cc-325cc байка!",
            whatsNew = "Сенсорные стрелки управления.",
            version = "v1.2",
            updatedDate = "5 июля 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 14,
            primaryColor = 0xFF424242,
            secondaryColor = 0xFF212121,
            iconSymbol = "motorcycle",
            apkFileName = "GravityDefied_v1.2.apk",
            apkDownloadUrl = "https://archive.org/download/gravity-defied-android/GravityDefied.apk",
            screenshots = listOf(
                ScreenshotItem("Векторные линии", "Тонкий баланс газа и тормоза", listOf(0xFF424242, 0xFF000000), "two_wheeler")
            ),
            reviews = listOf(
                Review("rev_gd1", "SiemensC65", 0xFF424242, 5, "9 октября 2013 г.", "Слезы ностальгии наворачиваются!", 830)
            )
        ),
        AppItem(
            id = "temple_run",
            name = "Temple Run (APK)",
            developer = "Imangi Studios",
            category = AppCategory.GAMES,
            packageName = "com.imangi.templerun",
            rating = 4.7f,
            reviewsCount = "4 500 000",
            downloadsCount = "500 000 000+",
            sizeText = "24,8 МБ",
            androidVersion = "Android 2.1 и выше",
            shortDescription = "Бегите по древним стенам храма от злых демонических обезьян!",
            fullDescription = "Побег из проклятого храма: собирайте золотые монеты и уворачивайтесь от ловушек!",
            whatsNew = "Оптимизация производительности.",
            version = "v1.0.9",
            updatedDate = "11 июня 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 15,
            primaryColor = 0xFF5D4037,
            secondaryColor = 0xFF3E2723,
            iconSymbol = "temple",
            apkFileName = "TempleRun_v1.0.9.apk",
            apkDownloadUrl = "https://archive.org/download/temple-run-classic/TempleRun.apk",
            screenshots = listOf(
                ScreenshotItem("Побег от обезьян", "Не оглядывайтесь назад!", listOf(0xFF5D4037, 0xFF1B0000), "run_circle")
            ),
            reviews = listOf(
                Review("rev_tr1", "Археолог", 0xFF5D4037, 5, "2 октября 2013 г.", "Классика жанра!", 310)
            )
        ),
        AppItem(
            id = "shazam_classic",
            name = "Shazam (Определитель музыки APK)",
            developer = "Shazam Entertainment",
            category = AppCategory.MEDIA,
            packageName = "com.shazam.android",
            rating = 4.8f,
            reviewsCount = "4 900 000",
            downloadsCount = "100 000 000+",
            sizeText = "7,5 МБ",
            androidVersion = "Android 2.3 и выше",
            shortDescription = "Узнайте название любой песни из радио или клуба за 5 секунд!",
            fullDescription = "Нажмите на синюю кнопку Shazam и распознайте любой трек в эфире!",
            whatsNew = "Ускорен алгоритм распознавания треков.",
            version = "v4.1.2",
            updatedDate = "22 августа 2013 г.",
            isEditorChoice = true,
            isTopFree = true,
            price = "Бесплатно",
            rank = 16,
            primaryColor = 0xFF0288D1,
            secondaryColor = 0xFF01579B,
            iconSymbol = "shazam",
            apkFileName = "Shazam_v4.1.2.apk",
            apkDownloadUrl = "https://archive.org/download/shazam-retro/Shazam.apk",
            screenshots = listOf(
                ScreenshotItem("Синяя кнопка", "Слушает музыку через микрофон", listOf(0xFF0288D1, 0xFF004BA0), "graphic_eq")
            ),
            reviews = listOf(
                Review("rev_sh1", "РадиоСлушатель", 0xFF0288D1, 5, "1 октября 2013 г.", "Нашел песню за 3 секунды!", 610)
            )
        )
    )
}
