"use client"

import { Search, RefreshCw, FileText, ChevronRight } from "lucide-react"
import { Card } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"

export default function PluginUIMockup() {
  const todos = [
    {
      id: 1,
      text: "Implement user authentication",
      file: "UserService.kt",
      line: 45,
      path: "src/main/kotlin/com/example/UserService.kt",
    },
    {
      id: 2,
      text: "Add error handling for network requests",
      file: "NetworkManager.kt",
      line: 23,
      path: "src/main/kotlin/com/example/NetworkManager.kt",
    },
    {
      id: 3,
      text: "Optimize database queries",
      file: "DatabaseHelper.kt",
      line: 67,
      path: "src/main/kotlin/com/example/DatabaseHelper.kt",
    },
    {
      id: 4,
      text: "Add unit tests for validation logic",
      file: "ValidationUtils.kt",
      line: 12,
      path: "src/main/kotlin/com/example/ValidationUtils.kt",
    },
    {
      id: 5,
      text: "Refactor legacy code in payment module",
      file: "PaymentProcessor.kt",
      line: 89,
      path: "src/main/kotlin/com/example/PaymentProcessor.kt",
    },
  ]

  return (
    <div className="w-full max-w-7xl mx-auto bg-gray-50 p-4">
      <div className="bg-white rounded-lg shadow-lg overflow-hidden">
        {/* IDE Header */}
        <div className="bg-gray-800 text-white px-4 py-2 flex items-center justify-between">
          <div className="flex items-center space-x-4">
            <div className="text-sm font-medium">IntelliJ IDEA</div>
            <div className="text-xs text-gray-300">Kotlin TODO Tracker Plugin</div>
          </div>
          <div className="flex space-x-2">
            <div className="w-3 h-3 bg-red-500 rounded-full"></div>
            <div className="w-3 h-3 bg-yellow-500 rounded-full"></div>
            <div className="w-3 h-3 bg-green-500 rounded-full"></div>
          </div>
        </div>

        {/* Main IDE Layout */}
        <div className="flex h-[600px]">
          {/* Left Sidebar - Project Tree */}
          <div className="w-64 bg-gray-100 border-r border-gray-300">
            <div className="p-3 border-b border-gray-300">
              <div className="text-sm font-medium text-gray-700">Project</div>
            </div>
            <div className="p-2 space-y-1">
              <div className="flex items-center text-sm text-gray-600">
                <ChevronRight className="w-4 h-4 mr-1" />
                <FileText className="w-4 h-4 mr-2" />
                kotlin-todo-project
              </div>
              <div className="ml-6 space-y-1">
                <div className="text-sm text-gray-600">src/main/kotlin</div>
                <div className="ml-4 space-y-1">
                  <div className="text-sm text-blue-600">UserService.kt</div>
                  <div className="text-sm text-gray-600">NetworkManager.kt</div>
                  <div className="text-sm text-gray-600">DatabaseHelper.kt</div>
                  <div className="text-sm text-gray-600">ValidationUtils.kt</div>
                  <div className="text-sm text-gray-600">PaymentProcessor.kt</div>
                </div>
              </div>
            </div>
          </div>

          {/* Center - Code Editor */}
          <div className="flex-1 bg-white">
            {/* Editor Tabs */}
            <div className="bg-gray-100 border-b border-gray-300 px-4 py-2">
              <div className="flex space-x-4">
                <div className="bg-white px-3 py-1 rounded-t border-t border-l border-r border-gray-300 text-sm font-medium">
                  UserService.kt
                </div>
                <div className="px-3 py-1 text-sm text-gray-600">NetworkManager.kt</div>
              </div>
            </div>

            {/* Code Content */}
            <div className="p-4 font-mono text-sm">
              <div className="space-y-1">
                <div className="text-gray-600">
                  <span className="text-gray-400 mr-4">40</span>
                  <span className="text-purple-600">class</span> <span className="text-blue-600">UserService</span>{" "}
                  {"{"}
                </div>
                <div className="text-gray-600">
                  <span className="text-gray-400 mr-4">41</span>
                  <span className="ml-4 text-purple-600">private</span> <span className="text-purple-600">val</span>{" "}
                  <span className="text-blue-600">users</span> = <span className="text-green-600">mutableListOf</span>()
                </div>
                <div className="text-gray-600">
                  <span className="text-gray-400 mr-4">42</span>
                </div>
                <div className="text-gray-600">
                  <span className="text-gray-400 mr-4">43</span>
                  <span className="ml-4 text-purple-600">fun</span>{" "}
                  <span className="text-blue-600">authenticateUser</span>(
                  <span className="text-orange-600">username</span>: <span className="text-green-600">String</span>){" "}
                  {"{"}
                </div>
                <div className="text-gray-600">
                  <span className="text-gray-400 mr-4">44</span>
                  <span className="ml-8 text-gray-500">// Validate input parameters</span>
                </div>
                <div className="bg-yellow-100 border-l-4 border-yellow-400">
                  <span className="text-gray-400 mr-4">45</span>
                  <span className="ml-8 text-gray-500">
                    // <span className="bg-yellow-200 px-1">TODO: Implement user authentication</span>
                  </span>
                </div>
                <div className="text-gray-600">
                  <span className="text-gray-400 mr-4">46</span>
                  <span className="ml-8 text-purple-600">return</span>
                </div>
                <div className="text-gray-600">
                  <span className="text-gray-400 mr-4">47</span>
                  <span className="ml-4">{"}"}</span>
                </div>
                <div className="text-gray-600">
                  <span className="text-gray-400 mr-4">48</span>
                  <span>{"}"}</span>
                </div>
              </div>
            </div>
          </div>

          {/* Right Sidebar - TODO Tracker Panel */}
          <div className="w-80 bg-gray-50 border-l border-gray-300">
            {/* Panel Header */}
            <div className="bg-gray-200 px-4 py-3 border-b border-gray-300">
              <div className="flex items-center justify-between">
                <h3 className="text-sm font-semibold text-gray-700">TODO Tracker</h3>
                <Button variant="ghost" size="sm" className="h-6 w-6 p-0">
                  <RefreshCw className="w-4 h-4" />
                </Button>
              </div>
            </div>

            {/* Filter Section */}
            <div className="p-3 border-b border-gray-300">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400" />
                <Input placeholder="Filter TODOs..." className="pl-10 h-8 text-sm" />
              </div>
            </div>

            {/* TODO List */}
            <div className="flex-1 overflow-y-auto">
              <div className="p-2 space-y-1">
                {todos.map((todo, index) => (
                  <Card
                    key={todo.id}
                    className={`p-3 cursor-pointer transition-colors hover:bg-blue-50 border ${
                      index === 0 ? "bg-blue-50 border-blue-200" : "border-gray-200"
                    }`}
                  >
                    <div className="space-y-2">
                      <div className="text-sm font-medium text-gray-800 line-clamp-2">{todo.text}</div>
                      <div className="flex items-center justify-between text-xs text-gray-500">
                        <span className="font-medium">{todo.file}</span>
                        <span>Line {todo.line}</span>
                      </div>
                      <div className="text-xs text-gray-400 truncate">{todo.path}</div>
                    </div>
                  </Card>
                ))}
              </div>
            </div>

            {/* Panel Footer */}
            <div className="bg-gray-200 px-4 py-2 border-t border-gray-300">
              <div className="text-xs text-gray-600">{todos.length} TODOs found</div>
            </div>
          </div>
        </div>

        {/* Status Bar */}
        <div className="bg-gray-800 text-white px-4 py-1 text-xs flex items-center justify-between">
          <div className="flex items-center space-x-4">
            <span>Kotlin TODO Tracker: Active</span>
            <span>•</span>
            <span>5 TODOs detected</span>
          </div>
          <div className="flex items-center space-x-4">
            <span>UTF-8</span>
            <span>•</span>
            <span>LF</span>
            <span>•</span>
            <span>Kotlin</span>
          </div>
        </div>
      </div>

      {/* Plugin Features Callouts */}
      <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card className="p-4">
          <div className="flex items-center space-x-3">
            <div className="w-8 h-8 bg-yellow-100 rounded-full flex items-center justify-center">
              <Search className="w-4 h-4 text-yellow-600" />
            </div>
            <div>
              <h4 className="font-semibold text-sm">Smart Detection</h4>
              <p className="text-xs text-gray-600">Automatically scans Kotlin files for TODO comments</p>
            </div>
          </div>
        </Card>

        <Card className="p-4">
          <div className="flex items-center space-x-3">
            <div className="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center">
              <FileText className="w-4 h-4 text-blue-600" />
            </div>
            <div>
              <h4 className="font-semibold text-sm">Inline Highlighting</h4>
              <p className="text-xs text-gray-600">Highlights TODO comments directly in the editor</p>
            </div>
          </div>
        </Card>

        <Card className="p-4">
          <div className="flex items-center space-x-3">
            <div className="w-8 h-8 bg-green-100 rounded-full flex items-center justify-center">
              <ChevronRight className="w-4 h-4 text-green-600" />
            </div>
            <div>
              <h4 className="font-semibold text-sm">Quick Navigation</h4>
              <p className="text-xs text-gray-600">Click any TODO to jump directly to its location</p>
            </div>
          </div>
        </Card>
      </div>
    </div>
  )
}
