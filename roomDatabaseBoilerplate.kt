@Database(entities = [], version = 1, exportSchema = false)
abstract class NameDatabase : RoomDatabase() {
    
    // Declare Daos
    abstract val classDao: ClassDao

    companion object {
        private var INSTANCE: NameDatabase? = null
        @Volatile
        fun getInstance(context: Context): NameDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =  Room.databaseBuilder(
                                        context.applicationContext,
                                        NameDatabase::class.java,
                                        "name_database"
                                )
                                .fallbackToDestructiveMigration()
                                .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}