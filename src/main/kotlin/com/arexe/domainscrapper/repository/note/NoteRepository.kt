package com.arexe.domainscrapper.repository.note

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


const val notesCollection = "notes"

@Repository
class NoteRepository(
    private val noteCrudRepository: NoteCrudRepository
) {
    suspend fun findAll() = noteCrudRepository.findAll()

    suspend fun findById(id: String) = noteCrudRepository.findById(id)

    suspend fun save(note: Note) = noteCrudRepository.save(note)

    suspend fun update(id: String, note: Note) = noteCrudRepository.findById(id)?.let {
        it.title = note.title
        it.content = note.content
        it.status = note.status
        noteCrudRepository.save(it)
    }

    suspend fun deleteById(id: String) = noteCrudRepository.deleteById(id)

    suspend fun count() = noteCrudRepository.count()
}

@Repository
interface NoteCrudRepository : CoroutineCrudRepository<Note, String>

@Document(collection = notesCollection)
data class Note(
    @Id val id: String = ObjectId().toString(),
    var title: String? = null,
    var content: String? = null,
    var status: NoteStatus = NoteStatus.ToDo,
    val creationDate: LocalDateTime = LocalDateTime.now()
)

enum class NoteStatus {
    ToDo,
    InProgress,
    Done,
    Abandoned
}