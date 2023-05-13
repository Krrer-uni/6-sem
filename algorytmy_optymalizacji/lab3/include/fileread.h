//
// Created by krrer on 11.05.23.
//

#ifndef LAB3_SRC_FILEREAD_H_
#define LAB3_SRC_FILEREAD_H_
#include "graph_utill.h"

class Filereader{
 public:
  static std::shared_ptr<Graph> readGraph(const std::string& filename);
  static std::shared_ptr<SsSources> readSSfile(const std::string& filename);
  static std::shared_ptr<P2pSsources> readP2Pfile(
      const std::string& filename);
};
#endif //LAB3_SRC_FILEREAD_H_
