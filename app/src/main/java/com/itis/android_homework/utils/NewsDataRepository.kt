package com.itis.android_homework.utils

import com.itis.android_homework.R
import com.itis.android_homework.model.NewsDataModel
import kotlin.random.Random


object NewsDataRepository {

    private val newsList = mutableListOf<NewsDataModel>()

    init {
        val list = listOf(
            NewsDataModel(
                newsId = 1,
                newsTitle = "Заседание общественной палаты",
                newsDetails = "Вчера прошло мальдивское заседание общественной палаты на берегу какого-то там залива",
                newsImage = R.drawable.news,
            ),
            NewsDataModel(
                newsId = 2,
                newsTitle = "В России готовятся к празднованию Хэллоуина",
                newsDetails = "Хэллоуин - cовременный международный праздник, восходящий к традициям древних кельтов " +
                        "Ирландии и Шотландии, история которого началась на территории современных Великобритании " +
                        "и Северной Ирландии",
                newsImage = R.drawable.news,
            ),
            NewsDataModel(
                newsId = 3,
                newsTitle = "Дополнительный выходной в Татарстане",
                newsDetails = "6 ноября мы отмечаем знаменательный праздник для нашей республики – День принятия " +
                        "Конституции Республики Татарстан",
                newsImage = R.drawable.news,
            ),
            NewsDataModel(
                newsId = 4,
                newsTitle = "Рассматривается вопрос открытия центра русского языка в Китае при поддержке КФУ",
                newsDetails = "Сегодня представители КФУ во главе с проректором по внешним связям Тимирханом Алишевым " +
                        "посетили два университета в провинции Гуандун. В состав делегации вошли также директор " +
                        "Института информационных технологий и интеллектуальных систем Михаил Абрамский, " +
                        "директор Института дизайна и пространственных искусств Карина Набиуллина, заместитель " +
                        "директора по международной деятельности Института международных отношений Альбина Имамутдинова, " +
                        "представители подготовительного факультета КФУ.",
                newsImage = R.drawable.news,
            ),
            NewsDataModel (
                newsId = 5,
                newsTitle = "Вводятся новые меры по борьбе с климатическими изменениями",
                newsDetails = "В осень 2023 года в России вводятся новые меры по борьбе с климатическими изменениями. В рамках стратегии устойчивого развития, правительство Российской Федерации принимает ряд новых законов и политик, направленных на снижение выбросов парниковых газов, развитие возобновляемых источников энергии и улучшение экологической ситуации в стране. Данные меры являются важным шагом для обеспечения экологической безопасности и будущего поколения." ,
                newsImage = R.drawable.news,
            ),
            NewsDataModel (
                newsId = 6,
                newsTitle = "Новость 6",
                newsDetails = "Описание новости 6",
                newsImage = R.drawable.news,
            ),
            NewsDataModel (
                newsId = 7,
                newsTitle = "Новость 7",
                newsDetails = "Описание новости 7",
                newsImage = R.drawable.news,
            ),
            NewsDataModel (
                newsId = 8,
                newsTitle = "Новость 8",
                newsDetails = "Описание новости 8",
                newsImage = R.drawable.news,
            ),
            NewsDataModel (
                newsId = 9,
                newsTitle = "Новость 9",
                newsDetails = "Описание новости 9",
                newsImage = R.drawable.news,
            ),
            NewsDataModel (
                newsId = 10,
                newsTitle = "Новость 10",
                newsDetails = "Описание новости 10",
                newsImage = R.drawable.news,
            ),
            NewsDataModel (
                newsId = 11,
                newsTitle = "Новость 11",
                newsDetails = "Описание новости 11",
                newsImage = R.drawable.news,
            ),
            NewsDataModel (
                newsId = 12,
                newsTitle = "Новость 12",
                newsDetails = "Описание новости 12",
                newsImage = R.drawable.news,
            )
        )
        newsList.addAll(list)
    }

    fun getNewsList(): MutableList<NewsDataModel> = newsList

    fun getNews(newsCount: Int): MutableList<NewsDataModel> {
        val array = getNewsList()

        val resultList = mutableListOf<NewsDataModel>()
        repeat(newsCount) {
            val newIndex = Random.nextInt(0, array.size)
            resultList.add(array[newIndex])
        }
        return resultList
    }
}