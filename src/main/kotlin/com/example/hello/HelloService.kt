package com.example.hello

data class ArticleModel(
    val title: String,
    val body: String
)

interface HelloService {
    suspend fun getList(): List<Article>

    suspend fun created(model: ArticleModel): Article?
}

class HelloServiceImpl(
    private val daoFacade: DAOFacade
) : HelloService {
    override suspend fun getList(): List<Article> = daoFacade.allArticles()

    override suspend fun created(model: ArticleModel): Article? = daoFacade.addNewArticle(model.title, model.title)

}
