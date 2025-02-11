import json
import logging

from openai import OpenAI
from lib import funcs

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)

client = OpenAI(
    api_key="sk-SgvKCkKZMKjQua9Y45D61aF17c824832A6Ff339544A7E1A4",
    base_url="https://aiapi.cubeai.com.cn/v1",
)

model = "gpt-3.5-turbo",
# messages = [{"role": "user", "content": "上周日本多少度?是否影响了我们这个月的手机销量？"}]
messages = [{"role": "user", "content": "以后每周六上午9点给我推送国际新闻"}]

completion = client.chat.completions.create(
    model="gpt-3.5-turbo",
    messages=messages,
    tools=funcs.tools
)

messages.append(completion.choices[0].message)

for tool_call in completion.choices[0].message.tool_calls:
    func_name = tool_call.function.name
    func_args = json.loads(tool_call.function.arguments)
    func_result = funcs.dispatch(func_name, func_args)
    messages.append({
        "role": "tool",
        "tool_call_id": tool_call.id,
        "content": str(func_result)
    })


completion_2 = client.chat.completions.create(
    model="gpt-3.5-turbo",
    messages=messages,
    tools=funcs.tools,
)

print(completion_2.choices[0].message.content)
