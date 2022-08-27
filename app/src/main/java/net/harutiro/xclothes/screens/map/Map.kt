package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import net.harutiro.xclothes.models.login.get.GetLoginResponse
import net.harutiro.xclothes.models.map.get.GetMapResponse
import net.harutiro.xclothes.screens.home.HomeViewModel
import net.harutiro.xclothes.screens.map.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(viewModel: MapViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val mContext = LocalContext.current

    viewModel.myCoordinates()
    viewModel.myLikesGet()

    Scaffold(
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 70.dp),
    ) {
        Box(
            contentAlignment = Alignment.BottomStart
        ) {
            Map(it, viewModel)
        }
    }


}

@Composable
fun Map(paddingValues: PaddingValues, viewModel: MapViewModel) {


    val cameraPositionState = rememberCameraPositionState {}

    viewModel.getLocation() {
        viewModel.nowLocation = LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)

        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(viewModel.nowLocation, 15f))
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        cameraPositionState = cameraPositionState
    ) {
        viewModel.likes.value.forEach{ labels ->
            labels.forEach{ it ->

                val user = remember { mutableStateOf(GetLoginResponse()) }

                viewModel.userGet(it.send_user_id){ getUser ->
                    user.value = getUser
                }

                Marker(
                    state = MarkerState(position = LatLng(it.lat.toDouble(), it.lon.toDouble())),
                    title = " 年齢：${user.value.age} 性別：${if(user.value.gender == 1) "男性" else "女性"}",
                    snippet = ""
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.BottomStart,
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            clothes(
                viewModel = viewModel,
                items = viewModel.coordinates.value,
                indexChanged = {}
            )
        }
    }

}

@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class)
@Composable
fun clothes(viewModel: MapViewModel, items:MutableList<GetMapResponse>, indexChanged:(Int)-> Unit) {
    val lazyListState: LazyListState = rememberLazyListState()
    val layoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState)

    var screenWidth = 0.dp

    BoxWithConstraints {
        screenWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
    }
    val itemWidthDp = 100f.dp
    val xForCenteredItemDp = ((screenWidth - itemWidthDp) / 2)


    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress) {
            // The scroll (fling) has finished, get the current item and
            // do something with it!
            val snappedItem = layoutInfo.currentItem
            Log.d("index", snappedItem.toString())

            indexChanged(snappedItem?.index ?: 0)
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        state = lazyListState,
        flingBehavior = rememberSnapperFlingBehavior(layoutInfo),
    ) {

        item {
            Box(
                modifier = Modifier.width(xForCenteredItemDp)
            )
        }

        itemsIndexed(
            items = items,
            key = { index, i ->
                i.id
            }

        ) { index, item->

            AnimatedVisibility(
                modifier = Modifier.animateItemPlacement(),
                visible = items.contains(item),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {

                Card(Modifier
                    .width(itemWidthDp)
                    .clickable {
                        viewModel.likeGet(item.id){
                            viewModel.likes.value = listOf(it)
                        }
                    }
                ) {

                    MapPhotoView(urlRemember = item.image)


                    val likePeople = remember { mutableStateOf(0) }

                    viewModel.likeGet(item.id){
                        likePeople.value = it.size
                    }

                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp, 4.dp),
                        text = "\uD83D\uDC96 ${likePeople.value}"
                    )

                }
            }

        }

        item {
            Box(
                modifier = Modifier.width(xForCenteredItemDp)
            )
        }
    }
}

@Composable
fun MapPhotoView(urlRemember:String){
    AsyncImage(
        model = urlRemember,
        contentDescription = "My Picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )

}

@Composable
@Preview
fun MapScreenPreview() {
    MapScreen()
}