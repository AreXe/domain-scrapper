package com.arexe.domainscrapper.note

import com.arexe.domainscrapper.config.BadRequestException
import com.arexe.domainscrapper.repository.note.Note
import com.arexe.domainscrapper.repository.note.NoteRepository
import com.arexe.domainscrapper.repository.note.NoteStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/notes")
class NoteController(
    private val noteRepository: NoteRepository
) {
    @GetMapping
    suspend fun findAllNotes() = noteRepository.findAll()
        .let { ResponseEntity.ok(it) }

    @GetMapping("/{id}")
    suspend fun getNoteById(@PathVariable id: String) = noteRepository.findById(id)
        ?.let { ResponseEntity.ok(it) }
        ?: ResponseEntity.notFound().build()

    @PostMapping
    suspend fun addNote(@RequestBody note: Note) = noteRepository.findById(note.id)?.let {
        throw BadRequestException("Note with given ID already exists, try update method")
    } ?: noteRepository.save(note).also { ResponseEntity.ok(it) }

    @PutMapping("/{id}")
    suspend fun updateNoteById(@PathVariable id: String, @RequestBody note: Note) =
        noteRepository.update(id, note)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build<Void>()

    @DeleteMapping("/{id}")
    suspend fun deleteNoteById(@PathVariable id: String) =
        noteRepository.findById(id)?.let {
            noteRepository.deleteById(id).let { ResponseEntity.ok().build() }
        } ?: ResponseEntity.notFound().build<Void>()

    @GetMapping("/count")
    suspend fun countAllNotes() = noteRepository.count().let { ResponseEntity.ok(it) }

    @GetMapping("/statuses")
    suspend fun availableStatuses() = NoteStatus.values().let { ResponseEntity.ok(it) }
}