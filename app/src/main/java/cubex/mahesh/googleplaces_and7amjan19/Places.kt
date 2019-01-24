package cubex.mahesh.googleplaces_and7amjan19

import com.google.gson.annotations.SerializedName

data class Places(@SerializedName("next_page_token")
                  val nextPageToken: String = "",
                  @SerializedName("results")
                  val results: List<ResultsItem>?,
                  @SerializedName("status")
                  val status: String = "")