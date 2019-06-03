class NameAdapter(val clickListener: ThisAdapterClickListener) : ListAdapter<ClassName, NameAdapter.ViewHolder>(AdapterDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutNameBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemLayoutNameBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ClassName, clickListener: ThisAdapterClickListener) {
            binding.className = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

class AdapterDiffCallback : DiffUtil.ItemCallback<ClassName>(){
    override fun areItemsTheSame(oldItem: ClassName, newItem: ClassName): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: ClassName, newItem: ClassName): Boolean {
        return oldItem == newItem
    }
}

class ThisAdapterClickListener(val clickListener: (toReturnParam: Long) -> Unit){
    fun onClick(receivedParam: Class) = clickListener(toReturnParam)
}