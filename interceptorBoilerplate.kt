class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)
        val rawJson = response.body()!!.string()
        val headers = response.headers()!!.toString()

        Log.d(BuildConfig.APPLICATION_ID, String.format("Response headers %s", headers))
        Log.d(BuildConfig.APPLICATION_ID, String.format("Response body %s", rawJson))

        // Re-create the response before returning it because body can be read only once
        return response.newBuilder()
            .body(ResponseBody.create(response.body()!!.contentType(), rawJson)).build()
    }

}