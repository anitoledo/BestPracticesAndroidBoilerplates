class NameViewModelFactory(private val param:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NameViewModel::class.java)) {
            return NameViewModel(param) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}