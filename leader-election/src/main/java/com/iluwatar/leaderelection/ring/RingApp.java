/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;
import com.iluwatar.leaderelection.MessageType;

import java.util.HashMap;
import java.util.Map;

/**
 * Example of how to use ring leader election. Initially 5 instances is created in the clould
 * system, and the instance with ID 1 is set as leader. After the system is started stop the
 * leader instance, and the new leader will be elected.
 */
public class RingApp {

  /**
   * Program entry point
   */
  public static void main(String[] args) {

    Map<Integer, Instance> instanceMap = new HashMap<>();
    MessageManager messageManager = new RingMessageManager(instanceMap);

    RingInstance instance1 = new RingInstance(messageManager, 1, 1);
    RingInstance instance2 = new RingInstance(messageManager, 2, 1);
    RingInstance instance3 = new RingInstance(messageManager, 3, 1);
    RingInstance instance4 = new RingInstance(messageManager, 4, 1);
    RingInstance instance5 = new RingInstance(messageManager, 5, 1);

    instanceMap.put(1, instance1);
    instanceMap.put(2, instance2);
    instanceMap.put(3, instance3);
    instanceMap.put(4, instance4);
    instanceMap.put(5, instance5);

    instance2.onMessage(new Message(MessageType.HEARTBEAT_INVOKE, ""));

    Thread thread1 = new Thread(instance1);
    Thread thread2 = new Thread(instance2);
    Thread thread3 = new Thread(instance3);
    Thread thread4 = new Thread(instance4);
    Thread thread5 = new Thread(instance5);

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
    thread5.start();

    instance1.setAlive(false);
  }
}
