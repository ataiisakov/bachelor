
import 'package:app_flutter/screens/detail.dart';
import 'package:flutter/material.dart';

import '../model/user_model.dart';

class ListUser extends StatefulWidget {
  const ListUser({super.key});

  @override
  State<StatefulWidget> createState() => _ListUser();
}

// List
class _ListUser extends State<ListUser> {
  late List<User> users;

  @override
  void initState() {
    users = Repository.instance.userList;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(child: Card(child: Text("Header"))),
        Expanded(child: _buildList()),
        Container(child: Card(child: Text("Footer")))
      ]
    );
  }
  Widget _buildList(){
    return ListView.builder(
        itemCount: users.length,
        itemBuilder: (BuildContext context, int index) {
          return _buildListTile(context, index);
        });
  }

  Widget _buildListTile(BuildContext context, int index) {
    return UserTile(user: users.elementAt(index));
  }
}

// List Item
class UserTile extends StatelessWidget {
  UserTile({required this.user}) : super(key: ObjectKey(user));

  final User user;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2, horizontal: 6),
      child: Card(
          child: ListTile(
            contentPadding: const EdgeInsets.symmetric(horizontal: 20,vertical: 15),
            title: Text(user.name),
            onTap: () {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => DetailScreen(user: user)));
            },
            leading: CircleAvatar(
              backgroundImage: NetworkImage(user.photoUrl),
            ),
          ),
        )
    );
  }
}
