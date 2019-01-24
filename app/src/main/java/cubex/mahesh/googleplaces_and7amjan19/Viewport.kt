package cubex.mahesh.googleplaces_and7amjan19

import com.google.gson.annotations.SerializedName

data class Viewport(@SerializedName("southwest")
                    val southwest: Southwest,
                    @SerializedName("northeast")
                    val northeast: Northeast)