// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.Message;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private Gson gson;
  private DatastoreService datastore;

  @Override
  public void init() {
    this.gson = new Gson();
    this.datastore = DatastoreServiceFactory.getDatastoreService();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ArrayList<Message> messages = new ArrayList<Message>();

    Query query = new Query("Message");
    PreparedQuery results = this.datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      String email = (String) entity.getProperty("email");
      String name = (String) entity.getProperty("name");
      String message = (String) entity.getProperty("message");

      Message messageObj = new Message(name, email, message);
      messages.add(messageObj);
    }

    String jsonMessage = convertToJson(messages);
    response.setContentType("application/json;");
    response.getWriter().println(jsonMessage);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String email = getParameterWithEmptyStringDefault(request, "email");
    String name = getParameterWithEmptyStringDefault(request, "name");
    String message = getParameterWithEmptyStringDefault(request, "message");

    if (email.isEmpty() || name.isEmpty() || message.isEmpty()) {
      response.setContentType("text/html;");
      response.getWriter().println("<meta http-equiv='refresh' content='3;URL=index.html'>");
      response.getWriter().println("<p style='color:red;'>All form fields must not be empty</p>");
      return;
    }

    Entity newEntity = new Entity("Message");
    newEntity.setProperty("email", email);
    newEntity.setProperty("name", name);
    newEntity.setProperty("message", message);
    this.datastore.put(newEntity);

    response.sendRedirect("/index.html");
  }

  private String getParameterWithEmptyStringDefault(HttpServletRequest request, String name) {
    String value = request.getParameter(name);
    if (value == null || value.isEmpty()) {
      return "";
    }
    return value;
  }

  private String convertToJson(Object any) {
    String json = this.gson.toJson(any);
    return json;
  }

}
