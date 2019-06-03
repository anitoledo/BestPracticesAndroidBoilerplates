private const val BASE_URL = ""

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val interceptor  = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

interface ApiService{
    @GET("get_url") // url option
    fun getObjects(@Query("filter") filter_param_name: String):
            Deferred<List<ClassName>>
}

object Api {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}