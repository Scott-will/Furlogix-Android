import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ListenableWorker
import com.example.vetapp.repositories.ReportEntryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class ReportCleanerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val reportEntryRepository: ReportEntryRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): ListenableWorker.Result {
        return withContext(Dispatchers.IO) {
            try {
                reportEntryRepository.deleteSentReportEntries()
                ListenableWorker.Result.success()
            } catch (exception: Exception) {
                ListenableWorker.Result.failure()
            }
        }
    }
}
