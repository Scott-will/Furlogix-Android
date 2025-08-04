import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.furlogix.Database.Entities.ReportEntry

@Composable
fun ReportHistoryTable(
    groupedEntries: Map<String, List<ReportEntry>>,
    modifier: Modifier = Modifier
) {
    val itemIds = groupedEntries.values
        .flatten()
        .distinct()
        .map{it.Id}
        .sorted()

    val timestamps = groupedEntries.keys.sorted()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp)
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 20.dp)
                ) {
                    // Timestamp column header
                    Text(
                        text = "Timestamp",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 16.sp,
                            letterSpacing = 0.5.sp
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .weight(1.2f)
                            .padding(horizontal = 12.dp)
                    )

                    itemIds.forEach { itemId ->
                        Text(
                            text = "Item $itemId",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)
                        )
                    }
                }

            }

            itemsIndexed(
                items = timestamps
            ) { rowIndex, timestamp ->
                val entriesForTimestamp = groupedEntries[timestamp] ?: emptyList()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (rowIndex % 2 == 0) Color.White else Color(0xFFF9FAFB)
                        )
                        .padding(vertical = 12.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Timestamp cell
                    Text(
                        text = timestamp,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color(0xFF1F2937),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .weight(1.2f)
                            .padding(horizontal = 12.dp)
                    )

                    // Value cells for each item ID
                    itemIds.forEach { itemId ->
                        val entryForItem = entriesForTimestamp.find { it.Id == itemId }
                        val cellValue = entryForItem?.value ?: ""

                        Text(
                            text = if (cellValue.isEmpty()) "—" else cellValue,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (cellValue.isEmpty()) Color(0xFFD1D5DB) else Color(0xFF374151),
                                fontSize = 14.sp,
                                fontWeight = if (cellValue.isEmpty()) FontWeight.Normal else FontWeight.Medium
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)
                        )
                    }
                }

                if (rowIndex < timestamps.size - 1) {
                    Divider(
                        color = Color(0xFFF3F4F6),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }
}