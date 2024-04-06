package com.exobe.customClicks

interface delete {
    fun delete(_id: String?)
    fun updatePrimaryAddress(_id: String?)
    fun selectedAddress(position:Int,id:String)
}

interface addressDelete{
    fun deleteAdd()
}

interface delete2{
    fun delete2()

}

interface UpdatePrimaryAddress{
    fun updateAddress(_id: String?)

}