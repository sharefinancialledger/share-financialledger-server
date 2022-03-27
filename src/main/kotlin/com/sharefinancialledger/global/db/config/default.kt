import com.improve_future.harmonica.core.DbConfig
import com.improve_future.harmonica.core.Dbms
import java.net.URI

class ExtractedDatasource(
        var dbName: String,
        var host: String,
        var port: Int,
        var user: String,
        var password: String
)

fun getExtractedDatasource(): ExtractedDatasource{
    val url: String = System.getenv("spring.datasource.url").substring(5)
    val uri: URI = URI.create(url)

    return ExtractedDatasource(uri.path, uri.host, uri.port, System.getenv("spring.datasource.username"),System.getenv("spring.datasource.password"))
}

class Default : DbConfig({
    val extractedDatasource = getExtractedDatasource()
    dbms = Dbms.MySQL
    dbName = extractedDatasource.dbName
    host = extractedDatasource.host
    port = extractedDatasource.port
    user = extractedDatasource.user
    password = extractedDatasource.password
})