import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IReportEntryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class ReportCleanerWorker @AssistedInject constructor(
    private val logger : ILogger,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val reportEntryRepository: IReportEntryRepository
) : CoroutineWorker(context, workerParams) {

    private val TAG = "Furlogix:" + ReportCleanerWorker::class.qualifiedName

    override suspend fun doWork(): ListenableWorker.Result {
        return withContext(Dispatchers.IO) {
            try {
                logger.log(TAG, "Starting report cleanup job")
                reportEntryRepository.deleteSentReportEntries()
                logger.log(TAG, "Report cleanup job success")
                ListenableWorker.Result.success()
            } catch (exception: Exception) {
                logger.log(TAG, "Report cleanup job failed ${exception.message}")
                ListenableWorker.Result.failure()
            }
        }
    }
}
