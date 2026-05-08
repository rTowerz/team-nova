from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Enable CORS for all routes

@app.route('/generate', methods=['POST'])
def generate_flashcards():
    data = request.get_json()
    topic = data.get('topic', '')

    # Mock flashcards based on topic
    flashcards = [
        {"question": f"What is the capital of {topic}?", "answer": f"The capital of {topic} is [Answer]."},
        {"question": f"What are some key facts about {topic}?", "answer": f"Key facts about {topic} include [Facts]."},
        {"question": f"Why is {topic} important?", "answer": f"{topic} is important because [Reason]."}
    ]

    return jsonify({"flashcards": flashcards})

if __name__ == '__main__':
    app.run(debug=True)