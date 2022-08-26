package net.harutiro.test_bottomnavigation_withjetpackcompose.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import net.harutiro.xclothes.activity.evaluation.TextBox
import net.harutiro.xclothes.models.coordinate.BrandList
import net.harutiro.xclothes.models.coordinate.CategoryList
import net.harutiro.xclothes.models.coordinate.CoordinateItems
import net.harutiro.xclothes.models.coordinate.PriceList
import net.harutiro.xclothes.models.map.get.GetMapResponse
import net.harutiro.xclothes.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val mContext = LocalContext.current

    viewModel.myCoordinates()

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
fun Map(paddingValues: PaddingValues, viewModel: HomeViewModel) {


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
        Marker(
            state = MarkerState(position = viewModel.nowLocation),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
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
                items = viewModel.coordinates.value,
                indexChanged = {}
            )
        }
    }

}

@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class)
@Composable
fun clothes(items:MutableList<GetMapResponse>, indexChanged:(Int)-> Unit) {
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

                Card(Modifier.width(itemWidthDp)) {
                    
                    MapPhotoView(urlRemember = item.image)

                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp,4.dp),
                        text = "\uD83D\uDC96１０"
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
fun HomeScreenPreview() {
    HomeScreen()
}