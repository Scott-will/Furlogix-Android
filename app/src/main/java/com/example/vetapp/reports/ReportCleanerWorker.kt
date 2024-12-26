import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ListenableWorker
import com.example.vetapp.repositories.IReportEntryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class ReportCleanerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val reportEntryRepository: IReportEntryRepository
) : CoroutineWorker(context, workerParams) {

    private val TAG = "VetApp:" + ReportCleanerWorker::class.qualifiedName

    override suspend fun doWork(): ListenableWorker.Result {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Starting report cleanup job")
                reportEntryRepository.deleteSentReportEntries()
                Log.d(TAG, "Report cleanup job success")
                ListenableWorker.Result.success()
            } catch (exception: Exception) {
                Log.d(TAG, "Report cleanup job failed ${exception.message}")
                ListenableWorker.Result.failure()
            }
        }
    }
}
