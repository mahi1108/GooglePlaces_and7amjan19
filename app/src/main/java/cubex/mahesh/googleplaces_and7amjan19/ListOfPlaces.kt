package cubex.mahesh.googleplaces_and7amjan19

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.list_places.view.*

class ListOfPlaces : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater,
       container: ViewGroup?, savedInstanceState: Bundle?): View? {

            var v = inflater.inflate(R.layout.list_places,
                                            container,false)

                var tempList = mutableListOf<String>()
                for( item in places_list!!)
                {
                    tempList.add("Name : ${item.name} \n  Vicinity : ${item.vicinity} Rating: ${item.rating}")
                }

                var myadapter = ArrayAdapter<String>(activity,
                    R.layout.myitem,tempList)
                v.lview.adapter = myadapter

            return  v
    }
}