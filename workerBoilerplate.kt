// Worker Class to execute (Is executed to refresh data even if app is shutted)
    class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params){
        companion object {
            const val WORK_NAME = ""
        }
        override suspend fun doWork(): Payload {
            val database = getDatabase(applicationContext)
            val repository = NameRepository(database)

            return try{
                repository.refreshObjects()
                Payload(Result.SUCCESS)
            } catch (e: HttpException){
                Payload(Result.RETRY)
            }
        }
    }

// Application Worker (for beign executed)
    // Application onCreate
    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }
    
    // Outside Application onCreate
    val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() = applicationScope.launch{
        setupRecurringWork()
    }

    private fun setupRecurringWork(){

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
        )
    }