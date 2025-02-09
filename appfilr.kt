package com.example.shoplist.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


data class Shopitem ( val id : Int,
                      var item : String,
                      var quantity : Int,
                      var isEdit : Boolean = false


)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Shoppingapp (){
    var sitem by remember { mutableStateOf(listOf<Shopitem>()) }
    var showa by remember { mutableStateOf(false) }
    var itemName by remember {mutableStateOf("")}
    var itemQuantity by remember {mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center) {

        Button(
            onClick = { showa = true},
            modifier = Modifier.align(Alignment.CenterHorizontally)

        ) {

            Text(text = "Add item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            items(sitem){
                item ->
                if (item.isEdit){
                    shopinglistEditor(item = item, OnEditingComplete = {
                        editedname , editedquantity ->
                        sitem = sitem.map{it.copy(isEdit = false)}
                        val editeditem = sitem.find { it.id == item.id }
                        editeditem?.let {
                            it.item = editedname
                            it.quantity = editedquantity

                        }
                    })
                } else {
                    shopingitem(item = item, onclick = {
                        sitem = sitem.map{it.copy(isEdit = it.id== item.id)}
                    }, ondelete = {
                        sitem = sitem - item

                    })



                }

            }
        }

    }
    
    if (showa){
        AlertDialog(
            onDismissRequest = { showa = false },
            confirmButton = {
                            Row (  modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween){
                             Button(onClick = {
                                 if (itemName.isNotBlank()){
                                     var newitem = Shopitem(
                                         id = sitem.size +1,
                                         item = itemName,
                                         quantity = itemQuantity.toInt()

                                     )
                                     sitem = sitem + newitem
                                     showa = false
                                     itemName =""
                                 }
                             }) {
                                 Text(text = "Add")
                             }
                                Button(onClick = {showa = false }
                                ) {
                                    Text(text = "Cancel")
                                }
                            }
            },
            title = { Text(text = "Add Shopping List") },
            text ={
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange ={itemName = it},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp))

                    OutlinedTextField(value = itemQuantity,
                        onValueChange ={itemQuantity = it},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp))
                }
                
            }

            )

        }
    }
@Composable
fun shopingitem(
    item: Shopitem,
    onclick: () -> Unit,
    ondelete: () -> Unit,
){
 Row(
     modifier = Modifier
         .fillMaxWidth()
         .padding(2.dp)
         .border(
             border = BorderStroke(2.dp, Color(0Xf6967578)),
             shape = RoundedCornerShape(20)
         ), horizontalArrangement = Arrangement.SpaceBetween
 ) {
     Text(text = item.item , modifier = Modifier.padding(8.dp))
     Text(text = "Quantity : ${item.quantity}" , modifier = Modifier.padding(8.dp))
     Row (modifier = Modifier.padding(8.dp)){
         IconButton(onClick = onclick) {
             Icon(imageVector = Icons.Default.Edit , contentDescription = null)
         }

         IconButton(onClick = ondelete) {
             Icon(imageVector = Icons.Default.Delete , contentDescription = null)
         }

     }

 }
}

@Composable
fun shopinglistEditor (item: Shopitem , OnEditingComplete : (String ,Int ) -> Unit){
    var editedname by remember { mutableStateOf(item.item) }
    var editedquantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember {mutableStateOf(item.isEdit)}
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .background(Color.Gray),
            horizontalArrangement = Arrangement.SpaceEvenly){

    Column {
      BasicTextField(
          value = editedname,
          onValueChange = {editedname = it},
          singleLine = true,
          modifier = Modifier
              .wrapContentSize()
              .padding(10.dp)
          )
        BasicTextField(
            value = editedquantity,
            onValueChange = {editedquantity = it},
            singleLine = true,
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        )

    }

       Button(onClick = {
           isEditing = false
           OnEditingComplete( editedname, editedquantity.toIntOrNull() ?: 1)

       }
       ) {
           Text(text = "Save")
       }


    }
}




