package com.example.taklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taklist.DataStorage.Task
import com.example.taklist.databinding.Fragment1Binding
import com.example.taklist.viewmodel.TaskViewModel
import com.example.taklist.viewmodel.TaskViewModelFactory

class Fragment1 : Fragment() {
    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = Fragment1Binding.inflate(inflater, container, false)

        setupRecyclerView()
        setupAddButton()
        observeTasks()

        return binding.root
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onDeleteClick = { task ->
                viewModel.delete(task)
            },
            onEditClick = { task ->
                showEditTaskDialog(task)
            },
            onStatusClick = { task ->
                viewModel.toggleTaskStatus(task)
            }
        )

        binding.taskRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }
    }

    private fun setupAddButton() {
        binding.add.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun observeTasks() {
        viewModel.allTasks.observe(viewLifecycleOwner) { tasks: List<Task> ->
            taskAdapter.submitList(tasks)
        }
    }

    private fun showAddTaskDialog() {
        val dialog = InputTaskDialog(requireContext()) { taskName ->
            viewModel.insert(Task(taskName = taskName))
        }
        dialog.show()
    }

    private fun showEditTaskDialog(task: Task) {
        val dialog = InputTaskDialog(requireContext()) { newTaskName ->
            val updatedTask = task.copy(taskName = newTaskName)
            viewModel.update(updatedTask)
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}